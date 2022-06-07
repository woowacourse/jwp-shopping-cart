package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static woowacourse.utils.Fixture.customer;
import static woowacourse.utils.Fixture.email;
import static woowacourse.utils.Fixture.password;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartSetRequest;
import woowacourse.shoppingcart.dto.CartSetResponse;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartDao cartDao;
    @Mock
    private CustomerDao customerDao;
    @InjectMocks
    private CartService cartService;
    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("장바구니에 물건이 없으면 저장한다.")
    void save_cart_not_exist() {
        // given
        Cart cart = new Cart(1L, 1L, 1000);
        given(customerDao.findIdByEmail(email))
                .willReturn(1L);
        given(cartDao.findByCustomerIdAndProductId(1L, cart.getProductId()))
                .willReturn(Optional.empty());
        given(cartDao.save(any(Cart.class)))
                .willReturn(cart);
        CartSetRequest cartSetRequest = new CartSetRequest(1000);
        // when
        CartSetResponse saved = cartService.setCart(cartSetRequest, email, 1L);

        // then
        assertThat(saved).isEqualTo(cart);
    }

    @Test
    @DisplayName("장바구니에 물건이 있으면 수정한다.")
    void set_cart_exist() {
        // given
        Cart cart = new Cart(1L, 1L, 1000);
        given(customerDao.findIdByEmail(email))
                .willReturn(1L);
        given(cartDao.findByCustomerIdAndProductId(1L, cart.getProductId()))
                .willReturn(Optional.of(cart));
        given(cartDao.update(any(Cart.class)))
                .willReturn(cart);
        CartSetRequest cartSetRequest = new CartSetRequest(1000);
        // when
        CartSetResponse updated = cartService.setCart(cartSetRequest, email, 1L);

        // then
        assertThat(updated).isEqualTo(cart);
    }

    @Test
    @DisplayName("장바구니에 물건을 조회한다.")
    void find_cart() {
        // given
        Cart cart = new Cart(1L, 1L, 1000);
        given(cartDao.findByCustomerIdAndProductId(1L, cart.getProductId()))
                .willReturn(Optional.of(cart));

        // when
        Cart find = cartService.findCartsByCustomerIdAndProductId(cart.getCustomerId(), cart.getProductId());

        // then
        assertThat(find).isEqualTo(cart);
    }
}
