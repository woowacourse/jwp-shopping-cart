package woowacourse.shoppingcart.ui;

import static org.assertj.core.api.Assertions.assertThat;

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
import woowacourse.shoppingcart.dto.AddCartItemRequest;

@SpringBootTest
@Sql("classpath:resetTables.sql")
class CartItemControllerTest {

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
        final ResponseEntity<Void> response = cartItemController.addCartItem(1L, new AddCartItemRequest(1L));

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
