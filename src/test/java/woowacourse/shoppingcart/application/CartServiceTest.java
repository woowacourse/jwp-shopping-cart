package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartProductRequest;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.SignUpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CartServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("장바구니 상품 담기")
    void addCart() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("greenlawn", "green@woowa.com", "123456");
        customerService.addCustomer(signUpRequest);

        ProductRequest productRequest = new ProductRequest("피자", 20000, "http://example.com/chicken.jpg");
        Product product = productService.addProduct(productRequest);

        //when & then
        assertThat(cartService.addCart(new CartProductRequest(product.getId()), "greenlawn"))
                .isNotNull();
    }
}