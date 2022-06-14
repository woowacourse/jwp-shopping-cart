package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@SpringBootTest
@Transactional
public class CartServiceTest {

    @Autowired
    private CartService cartService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String firstCustomerEmail = "test@test.com";
    private String firstCustomerName = "Bunny";
    private String firstCustomerPhone = "010-0000-0000";
    private String firstCustomerAddress = "서울시 종로구";
    private String firstCustomerPassword = "Bunny1234!@";

    private Long productId1;
    private Long productId2;
    private Long customerId;

    @BeforeEach
    void setUp() {
        Product product1 = new Product("치킨", 10_000, "http://example.com/chicken.jpg");
        Product product2 = new Product("맥주", 20_000, "http://example.com/beer.jpg");
        productId1 = productService.addProduct(product1);
        productId2 = productService.addProduct(product2);

        CustomerRequest firstCustomerRequest = new CustomerRequest(firstCustomerEmail,
                firstCustomerPassword,
                firstCustomerName,
                firstCustomerPhone,
                firstCustomerAddress);
        CustomerResponse createResponse = customerService.register(firstCustomerRequest);
        TokenRequest tokenRequest = new TokenRequest(firstCustomerEmail, firstCustomerPassword);
        TokenResponse tokenResponse = authService.login(tokenRequest);
        customerId = getIdByTokenResponse(tokenResponse);
    }

    @Test
    @DisplayName("Customer ID를 통해 장바구니 목록을 조회한다.")
    void findCartsByCustomerId() {
        // given
        Long cartId1 = cartService.addCart(customerId, productId1, 10);
        Long cartId2 = cartService.addCart(customerId, productId2, 5);
        // when
        List<Cart> carts = cartService.findCartsByCustomerId(customerId);
        // then
        assertThat(carts.size()).isEqualTo(2);
        assertThat(carts.stream().map(Cart::getQuantity)).containsExactly(10, 5);
    }

    @Test
    @DisplayName("장바구니에 상품 추가 성공")
    void addCart_success() {
        // when
        Long cartId = cartService.addCart(customerId, productId1, 10);
        // then
        assertThat(cartId).isNotNull();
    }

    @Test
    @DisplayName("장바구니 상품 추가 시 최소 장바구니 수량 미충족되면 예외를 반환한다.")
    void addCart_whenUnderMinimumQuantity_fail() {
        assertThatThrownBy(() -> cartService.addCart(customerId, productId1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 상품 수량 형식입니다.");
    }

    @Test
    @DisplayName("장바구니 상품 추가 시 상품 목록에 존재하지 않는 상품 추가할 경우 예외를 반환한다.")
    void addCart_whenNonExistProduct_fail() {
        assertThatThrownBy(() -> cartService.addCart(customerId, 18L, 10))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("해당하는 상품이 없습니다.");
    }

    @Test
    @DisplayName("장바구니에서 삭제한다.")
    void deleteCart_success() {
        // given
        Long cartId1 = cartService.addCart(customerId, productId1, 10);
        Long cartId2 = cartService.addCart(customerId, productId2, 5);
        // when
        assertThatCode(() -> cartService.deleteCart(customerId, List.of(productId1)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장바구니에 존재하지 않는 상품을 삭제 요청 시 예외를 반환한다.")
    void deleteCart_whenNonExistProduct_fail() {
        // given
        Long cartId1 = cartService.addCart(customerId, productId1, 10);
        Long cartId2 = cartService.addCart(customerId, productId2, 5);
        // when
        assertThatThrownBy(() -> cartService.deleteCart(customerId, List.of(3L)))
                .isInstanceOf(NotInCustomerCartItemException.class)
                .hasMessage("해당하는 상품이 없습니다.");
    }

    @Test
    @DisplayName("장바구니 물품의 수량을 수정한다.")
    void updateCart_success() {
        // when
        Long cartId1 = cartService.addCart(customerId, productId1, 10);
        Long cartId2 = cartService.addCart(customerId, productId2, 5);
        // then
        assertThatCode(() -> cartService.updateCart(customerId, productId1, 5))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("장바구니 상품 수정 시 장바구니 내 존재하지 않는 상품 수정하면 예외를 반환한다.")
    void updateCart_NonExistProduct_fail() {
        // when
        Long cartId1 = cartService.addCart(customerId, productId1, 10);
        // then
        assertThatThrownBy(() -> cartService.updateCart(customerId, productId2, 5))
                .isInstanceOf(NotInCustomerCartItemException.class)
                .hasMessage("해당하는 상품이 없습니다.");
    }


    @Test
    @DisplayName("장바구니 상품 수정 시 최소 장바구니 수량 미충족되면 예외를 반환한다.")
    void updateCart_whenUnderMinimumQuantity_fail() {
        Long cartId1 = cartService.addCart(customerId, productId1, 10);
        Long cartId2 = cartService.addCart(customerId, productId2, 5);
        assertThatThrownBy(() -> cartService.updateCart(customerId, productId1, -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 상품 수량 형식입니다.");
    }

    private Long getIdByTokenResponse(TokenResponse tokenResponse) {
        return Long.parseLong(jwtTokenProvider.getPayload(tokenResponse.getAccessToken()));
    }
}
