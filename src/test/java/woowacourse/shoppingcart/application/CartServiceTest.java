package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.request.ProductIdRequest;
import woowacourse.shoppingcart.application.dto.request.SignUpRequest;
import woowacourse.shoppingcart.application.dto.response.CartItemResponse;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.datanotfound.ProductDataNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"})
@DisplayName("Cart 서비스 테스트")
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @DisplayName("장바구니에 상품을 추가한다.")
    @Test
    void addCartItems() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        Long productId = productService.addProduct(new Product("초콜렛", 1_000, "www.test.com"));
        ProductIdRequest productIdRequest = new ProductIdRequest(productId);

        // when
        List<CartItemResponse> cartItemResponses = cartService.addCartItems(customerIdentificationRequest, List.of(productIdRequest));

        // then
        assertAll(
                () -> assertThat(cartItemResponses).hasSize(1),
                () -> assertThat(cartItemResponses.get(0).getQuantity()).isEqualTo(1)
        );
    }

    @DisplayName("장바구니에 추가한 상품이 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void addCartItemsException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        ProductIdRequest productIdRequest = new ProductIdRequest(1L);

        // when & then
        assertThatThrownBy(() -> cartService.addCartItems(customerIdentificationRequest, List.of(productIdRequest)))
                .isInstanceOf(ProductDataNotFoundException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }
}
