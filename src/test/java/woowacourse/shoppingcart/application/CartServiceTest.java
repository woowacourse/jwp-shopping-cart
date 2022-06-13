package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static woowacourse.fixture.PasswordFixture.encryptedBasicPassword;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.exception.QuantityRangeException;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private CartItemDao cartItemDao;

    @DisplayName("장바구니 수량을 업데이트 한다.")
    @Test
    void updateCartQuantity() {
        // given
        Long customerId = 1L;
        Long cartId = 1L;
        Customer customer = new Customer(customerId, new UserName("giron"), encryptedBasicPassword);
        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(cartItemDao.findIdsByCustomerId(customerId))
                .willReturn(List.of(cartId));
        willDoNothing().given(cartItemDao).updateQuantity(10, cartId);

        // when // then
        assertAll(
                () -> assertDoesNotThrow(() -> cartService.updateQuantity(cartId, 10, customerId)),
                () -> verify(customerDao).findById(customerId),
                () -> verify(cartItemDao).findIdsByCustomerId(customerId),
                () -> verify(cartItemDao).updateQuantity(10, cartId)
        );

    }

    @DisplayName("장바구니 수량을 업데이트할 때 적절한 수량이 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 100})
    void updateCartQuantityWithWrongValue(int quantity) {
        // given
        Long customerId = 1L;
        Long cartId = 1L;
        Customer customer = new Customer(customerId, new UserName("giron"), encryptedBasicPassword);
        given(customerDao.findById(customerId))
                .willReturn(Optional.of(customer));
        given(cartItemDao.findIdsByCustomerId(customerId))
                .willReturn(List.of(cartId));

        // when // then
        assertAll(
                () -> assertThatThrownBy(() -> cartService.updateQuantity(cartId, quantity, customerId))
                        .isExactlyInstanceOf(QuantityRangeException.class)
                        .hasMessageContaining("수량은 0~99개까지 가능합니다."),
                () -> verify(customerDao).findById(customerId),
                () -> verify(cartItemDao).findIdsByCustomerId(customerId)
        );
    }
}
