package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.common.exception.CartItemException;
import woowacourse.common.exception.NotFoundException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.dto.ProductRequest;

@SpringBootTest
@Sql("classpath:truncate.sql")
class CartServiceTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer("test@email.com", Password.fromPlainInput("password0!"), "name");
        customerDao.save(customer);
    }

    @Test
    @DisplayName("장바구니 값이 없을 경우 에러를 발생시킨다.")
    void findCartFailByNotExistingCart() {
        assertThatThrownBy(() -> cartService.findCart(1L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 제품을 장바구니에 넣는 경우 에러를 발생시킨다.")
    void addCartFailByProductNotExists() {
        assertThatThrownBy(() -> cartService.addCart(1L, 10, "test@email.com"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("이미 장바구니에 담겨있는 상품인 경우 에러를 발생시킨다.")
    void addCartFailByProductAlreadyExists() {
        //given
        ThumbnailImage image = new ThumbnailImage("url", "alt");
        ProductRequest productRequest = new ProductRequest("name", 1000, 10, image);
        productService.addProduct(productRequest);

        cartService.addCart(1L, 10, "test@email.com");

        //then
        assertThatThrownBy(() -> cartService.addCart(1L, 10, "test@email.com"))
                .isInstanceOf(CartItemException.class);
    }

    @Test
    @DisplayName("장바구니에 없는 상품을 삭제할 경우 에러를 발생시킨다..")
    void deleteCartFailByCartNotExist() {
        assertThatThrownBy(() -> cartService.deleteCarts("test@email.com", List.of(1L)))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("장바구니에 없는 상품을 수정할 경우 에러를 발생시킨다.")
    void updateCartFailByCartNotExist() {
        assertThatThrownBy(() -> cartService.updateCart("test@email.com", 1L, 10))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("수정하려는 용량이 0 이하의 값일 경우 에러를 발생시킨다.")
    void updateCartFailByInvalidQuantity() {
        //given
        ThumbnailImage image = new ThumbnailImage("url", "alt");
        ProductRequest productRequest = new ProductRequest("name", 1000, 10, image);
        productService.addProduct(productRequest);

        cartService.addCart(1L, 10, "test@email.com");

        //then
        assertThatThrownBy(() -> cartService.updateCart("test@email.com", 1L, -1))
                .isInstanceOf(CartItemException.class);
    }
}