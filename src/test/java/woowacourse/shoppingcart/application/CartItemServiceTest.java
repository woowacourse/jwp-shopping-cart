package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static woowacourse.fixture.PasswordFixture.encryptedBasicPassword;
import static woowacourse.fixture.ProductFixture.PRODUCT_BANANA;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.response.CartResponse;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private CustomerDao customerDao;

    @DisplayName("로그인 된 사용자의 장바구니를 조회한다.")
    @Test
    void findCartsByCustomerId() {
        // given
        final UserName userName = new UserName("giron");
        final Long customerId = 1L;
        final Customer customer = new Customer(customerId, userName, encryptedBasicPassword);
        final Cart banana = new Cart(1L, new Quantity(5), PRODUCT_BANANA);
        final List<Cart> carts = List.of(banana);

        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(cartItemDao.findAllJoinProductByCustomerId(customerId))
                .willReturn(carts);

        // when
        final List<CartResponse> cartResponses = cartService.findCartsByCustomerId(customerId);
        final CartResponse cartResponse = cartResponses.get(0);

        // then
        assertAll(
                () -> assertThat(cartResponse.getId()).isEqualTo(1L),
                () -> assertThat(cartResponse.getQuantity()).isEqualTo(5),
                () -> assertThat(cartResponse.getName()).isEqualTo("banana"),
                () -> assertThat(cartResponse.getPrice()).isEqualTo(1_000),
                () -> assertThat(cartResponse.getImageUrl()).isEqualTo("woowa1.com"),
                () -> verify(customerDao).findById(customerId),
                () -> verify(cartItemDao).findAllJoinProductByCustomerId(customerId)
        );
    }

    @DisplayName("로그인 된 사용자의 장바구니의 수량을 변경한다.")
    @Test
    void updateCartItemQuantity() {
        // given
        final UserName userName = new UserName("giron");
        final Long cartId = 1L;
        final Long customerId = 1L;
        final Customer customer = new Customer(customerId, userName, encryptedBasicPassword);

        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(cartItemDao.findIdsByCustomerId(customerId))
                .willReturn(List.of(cartId));
        doNothing().when(cartItemDao).updateQuantity(1, cartId);

        // when  // then
        assertAll(
                () -> assertDoesNotThrow(() -> cartService.updateQuantity(cartId, 1, customerId)),
                () -> verify(customerDao).findById(customerId),
                () -> verify(cartItemDao).findIdsByCustomerId(customerId),
                () -> verify(cartItemDao).updateQuantity(1, cartId)
        );
    }
}
