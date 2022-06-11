package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static woowacourse.utils.Fixture.email;
import static woowacourse.utils.Fixture.치킨;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.dto.cart.CartSetRequest;
import woowacourse.shoppingcart.dto.cart.CartSetResponse;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartDao cartDao;
    @Mock
    private CustomerDao customerDao;
    @Mock
    private ProductDao productDao;
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
        given(productDao.findProductById(any(Long.class)))
                .willReturn(Optional.of(치킨));

        // when
        CartSetResponse saved = cartService.addCart(new CartSetRequest(1000), email, 1L);

        // then
        assertAll(
                () -> assertThat(saved.getName()).isEqualTo(치킨.getName()),
                () -> assertThat(saved.getPrice()).isEqualTo(치킨.getPrice()),
                () -> assertThat(saved.getImage()).isEqualTo(치킨.getImage())
        );
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
        given(productDao.findProductById(any(Long.class)))
                .willReturn(Optional.of(치킨));
        given(cartDao.update(any(Cart.class)))
                .willReturn(new Cart(cart.getId(), cart.getCustomerId(), 2000));
        CartSetRequest cartSetRequest = new CartSetRequest(2000);
        // when
        CartSetResponse updated = cartService.addCart(cartSetRequest, email, 1L);

        // then
        assertAll(
                () -> assertThat(updated.getName()).isEqualTo(치킨.getName()),
                () -> assertThat(updated.getPrice()).isEqualTo(치킨.getPrice()),
                () -> assertThat(updated.getImage()).isEqualTo(치킨.getImage()),
                () -> assertThat(updated.getQuantity()).isEqualTo(2000)
        );
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
