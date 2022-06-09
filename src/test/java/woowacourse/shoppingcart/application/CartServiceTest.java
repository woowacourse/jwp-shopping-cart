package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.request.CartItemIdRequest;
import woowacourse.shoppingcart.application.dto.request.CartItemRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.request.ProductIdRequest;
import woowacourse.shoppingcart.application.dto.request.ProductRequest;
import woowacourse.shoppingcart.application.dto.request.SignUpRequest;
import woowacourse.shoppingcart.application.dto.response.CartItemResponse;
import woowacourse.shoppingcart.application.dto.response.CartResponse;
import woowacourse.shoppingcart.exception.dataformat.QuantityDataFormatException;
import woowacourse.shoppingcart.exception.datanotfound.ProductDataNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
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

    @DisplayName("장바구니에 담긴 상품 목록을 조회한다.")
    @Test
    void findCarts() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        Long productId = productService.addProduct(new ProductRequest("초콜렛", 1_000, "www.test.com"));
        ProductIdRequest productIdRequest = new ProductIdRequest(productId);
        cartService.addCartItems(customerIdentificationRequest, List.of(productIdRequest));

        // when
        List<CartResponse> cartResponses = cartService.findCarts(customerIdentificationRequest);

        // then
        assertAll(
                () -> assertThat(cartResponses).hasSize(1),
                () -> assertThat(cartResponses.get(0).getProductId()).isEqualTo(productId),
                () -> assertThat(cartResponses.get(0).getName()).isEqualTo("초콜렛"),
                () -> assertThat(cartResponses.get(0).getPrice()).isEqualTo(1_000),
                () -> assertThat(cartResponses.get(0).getImageUrl()).isEqualTo("www.test.com"),
                () -> assertThat(cartResponses.get(0).getQuantity()).isEqualTo(1)
        );
    }

    @DisplayName("장바구니에 상품을 추가한다.")
    @Test
    void addCartItems() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        Long productId = productService.addProduct(new ProductRequest("초콜렛", 1_000, "www.test.com"));
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

    @DisplayName("장바구니에 담긴 상품 개수를 수정한다.")
    @Test
    void updateQuantity() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        Long productId = productService.addProduct(new ProductRequest("초콜렛", 1_000, "www.test.com"));
        ProductIdRequest productIdRequest = new ProductIdRequest(productId);

        List<CartItemResponse> cartItemResponses = cartService.addCartItems(customerIdentificationRequest, List.of(productIdRequest));

        // when
        CartItemResponse cartItemResponse = cartService.updateQuantity(
                customerIdentificationRequest, new CartItemRequest(cartItemResponses.get(0).getId(), 1)
        );

        // then
        assertAll(
                () -> assertThat(cartItemResponse.getId()).isEqualTo(cartItemResponses.get(0).getId()),
                () -> assertThat(cartItemResponse.getQuantity()).isEqualTo(1)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("장바구니에 담긴 상품 개수를 수정할 때 개수가 1 미만이면 예외가 발생한다.")
    void updateQuantityException(int quantity) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        Long productId = productService.addProduct(new ProductRequest("초콜렛", 1_000, "www.test.com"));
        ProductIdRequest productIdRequest = new ProductIdRequest(productId);

        List<CartItemResponse> cartItemResponses = cartService.addCartItems(customerIdentificationRequest, List.of(productIdRequest));

        // when & then
        assertThatThrownBy(() -> cartService.updateQuantity(
                customerIdentificationRequest, new CartItemRequest(cartItemResponses.get(0).getId(), quantity)
        ))
        .isInstanceOf(QuantityDataFormatException.class)
        .hasMessage("수량은 1 이상이어야 합니다.");
    }

    @DisplayName("장바구니에 담긴 상품을 제거한다.")
    @Test
    void delete() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        Long productId = productService.addProduct(new ProductRequest("초콜렛", 1_000, "www.test.com"));
        ProductIdRequest productIdRequest = new ProductIdRequest(productId);

        List<CartItemResponse> cartItemResponses = cartService.addCartItems(customerIdentificationRequest, List.of(productIdRequest));

        // when & then
        assertThatCode(() -> cartService.deleteCarts(List.of(new CartItemIdRequest(cartItemResponses.get(0).getId()))))
                .doesNotThrowAnyException();
    }
}
