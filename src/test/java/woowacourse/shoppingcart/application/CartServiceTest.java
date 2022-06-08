package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.CartItemQuantityUpdateRequest;
import woowacourse.shoppingcart.application.dto.CartItemResponse;
import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.application.dto.ProductSaveRequest;

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
    @DisplayName("상품을 카트에 저장한다.")
    void saveCardItem() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password1234A!", "rookie"));
        productService.save(new ProductSaveRequest("상품1", 1000, "https://www.test.com"));

        // when & then
        assertDoesNotThrow(() -> cartService.saveCartItem(1L, 1L));
    }

    @Test
    @DisplayName("상품을 카드에 저장할 경우 기존에 동일 상품이 있으면 예외가 발생한다.")
    void saveCartDuplicatedItem() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password1234A!", "rookie"));
        productService.save(new ProductSaveRequest("상품1", 1000, "https://www.test.com"));
        cartService.saveCartItem(1L, 1L);

        // when & then
        assertThatThrownBy(() -> cartService.saveCartItem(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("고객 id를 통해서 카트에 저장된 상품들을 조회할 수 있다.")
    void findByCustomerId() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password1234A!", "rookie"));
        productService.save(new ProductSaveRequest("상품1", 1000, "https://www.test.com"));
        cartService.saveCartItem(1L, 1L);
        
        // when
        List<CartItemResponse> cartItemResponses = cartService.findAllByCustomerId(1L);

        // then
        assertThat(cartItemResponses).hasSize(1);
    }

    @Test
    @DisplayName("고객 id와 상품 id를 통해서 카트의 상품 수량을 변경할 수 있다.")
    void updateQuantity() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password1234A!", "rookie"));
        productService.save(new ProductSaveRequest("상품1", 1000, "https://www.test.com"));
        cartService.saveCartItem(1L, 1L);

        // when
        cartService.updateQuantity(new CartItemQuantityUpdateRequest(1L, 1L, 2));

        // then
        List<CartItemResponse> cartItems = cartService.findAllByCustomerId(1L);
        assertThat(cartItems.get(0).getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 id와 상품 id를 통해서 장바구니의 상품을 삭제할 수 있다.")
    void deleteCartItem() {
        // given
        customerService.save(new CustomerSaveRequest("email@email.com", "password1234A!", "rookie"));
        productService.save(new ProductSaveRequest("상품1", 1000, "https://www.test.com"));
        cartService.saveCartItem(1L, 1L);

        // when & then
        assertDoesNotThrow(() -> cartService.deleteCartItem(1L, 1L));
    }
}
