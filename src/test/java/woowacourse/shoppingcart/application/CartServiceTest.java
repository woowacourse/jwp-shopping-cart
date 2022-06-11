package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.global.exception.InvalidCartItemException;
import woowacourse.global.exception.InvalidProductException;
import woowacourse.shoppingcart.application.dto.CartResponse;
import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.application.dto.ProductSaveRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("장바구니에 상품을 등록한다.")
    void saveCart() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password123@Q", "zero"));
        productService.save(new ProductSaveRequest("치킨", 20_000, "test.url.com"));

        // when
        cartService.save(1L, 1L);
        List<CartResponse> actualResponse = cartService.findAll(1L);

        // then
        assertThat(actualResponse.get(0)).usingRecursiveComparison()
                .isEqualTo(new CartResponse(1L, "치킨", 20_000, 1, "test.url.com"));
    }

    @Test
    @DisplayName("장바구니에 이미 등록된 상품을 등록할 시 오류가 발생한다.")
    void saveDuplicateProduct() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password123@Q", "zero"));
        productService.save(new ProductSaveRequest("치킨", 20_000, "test.url.com"));

        // when
        cartService.save(1L, 1L);

        // then
        assertThatThrownBy(() -> cartService.save(1L, 1L))
                .isInstanceOf(InvalidCartItemException.class);
    }

    @Test
    @DisplayName("장바구니 전체를 조회한다.")
    void showCart() {
        // given
        customerService.save(new CustomerSaveRequest("email1@email.com", "password123@Q", "zero"));
        customerService.save(new CustomerSaveRequest("email2@email.com", "password123@Q", "rookie"));
        productService.save(new ProductSaveRequest("치킨", 20_000, "chicken.url.com"));
        productService.save(new ProductSaveRequest("샐러드", 10_000, "salad.url.com"));
        productService.save(new ProductSaveRequest("피자", 30_000, "pizza.url.com"));
        cartService.save(1L, 1L);
        cartService.save(2L, 2L);
        cartService.save(1L, 3L);

        // when
        List<CartResponse> cartResponses = cartService.findAll(1L);

        // then
        assertThat(cartResponses).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new CartResponse(1L, "치킨", 20_000, 1, "chicken.url.com"),
                        new CartResponse(3L, "피자", 30_000, 1, "pizza.url.com")
                ));
    }

    @Test
    @DisplayName("장바구니 상품 수량을 변경한다.")
    void updateCart() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password123@Q", "zero"));
        productService.save(new ProductSaveRequest("치킨", 20_000, "chicken.url.com"));
        cartService.save(1L, 1L);

        // when
        cartService.updateQuantity(1L, 1L, 2);
        cartService.updateQuantity(1L, 1L, 3);
        cartService.updateQuantity(1L, 1L, 2);
        List<CartResponse> actualResponse = cartService.findAll(1L);

        // then
        assertThat(actualResponse.get(0)).usingRecursiveComparison()
                .isEqualTo(new CartResponse(1L, "치킨", 20_000, 2, "chicken.url.com"));
    }

    @Test
    @DisplayName("장바구니 상품을 삭제한다.")
    void deleteCart() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password123@Q", "zero"));
        productService.save(new ProductSaveRequest("치킨", 20_000, "chicken.url.com"));
        cartService.save(1L, 1L);

        // when
        cartService.delete(1L, 1L);

        // then
        assertThatThrownBy(() -> cartService.findAll(1L))
                .isInstanceOf(InvalidProductException.class);
    }
}