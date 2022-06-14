package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.application.dto.CustomerSaveServiceRequest;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.cart.AddCartItemRequest;
import woowacourse.shoppingcart.dto.cart.CartDto;
import woowacourse.shoppingcart.exception.notfound.NotFoundException;

@SpringBootTest
@Sql("classpath:resetTables.sql")
class CartItemItemControllerTest {

    @Autowired
    CartItemController cartItemController;

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @Test
    @DisplayName("장바구니에 항목추가 하는 기능")
    void addCartItem() {
        // given
        customerService.save(new CustomerSaveServiceRequest("klay", "email@gmail.com", "12345678"));
        productService.addProduct(new Product("밥", 1000, "www.naver.com"));

        // when
        final ResponseEntity<Void> response = cartItemController.enrollCartItem(1L, new AddCartItemRequest(1L));

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("존재하지 않는 제품 id로 항목 추가하면 예외 발생하는 기능")
    void addCartItem_invalidProductId_throwsException() {
        // given
        customerService.save(new CustomerSaveServiceRequest("klay", "email@gmail.com", "12345678"));

        // when, then
        assertThatThrownBy(() -> cartItemController.enrollCartItem(1L, new AddCartItemRequest(1L)))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("장바구니에 저장된 항목 보는 기능")
    void getCartItems() {
        // given
        customerService.save(new CustomerSaveServiceRequest("klay", "email@gmail.com", "12345678"));
        productService.addProduct(new Product("밥", 1000, "www.naver.com"));
        cartItemController.enrollCartItem(1L, new AddCartItemRequest(1L));

        // when
        final ResponseEntity<List<CartDto>> cartItems = cartItemController.getCartItems(1L);

        // then
        assertThat(cartItems.getBody()).hasSize(1);
    }
}
