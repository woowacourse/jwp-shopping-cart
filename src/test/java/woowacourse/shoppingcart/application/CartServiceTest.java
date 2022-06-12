package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.application.dto.CartDeleteServiceRequest;
import woowacourse.shoppingcart.application.dto.CartSaveServiceRequest;
import woowacourse.shoppingcart.application.dto.CartUpdateServiceRequest;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.*;
import woowacourse.shoppingcart.application.dto.CartServiceResponse;
import woowacourse.shoppingcart.exception.AlreadyInCartException;
import woowacourse.shoppingcart.util.PasswordEncryptor;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartItemDao cartItemDao;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private ProductDao productDao;

    @DisplayName("장바구니에 품목을 추가한다.")
    @Test
    void add() {
        // given
        final CartSaveServiceRequest request = new CartSaveServiceRequest(1L, 5);
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));
        final Product product = new Product(1L, "치킨", 3000, "www.chicken.com");

        // when
        when(cartItemDao.addCartItem(customer.getId(), product.getId(), request.getQuantity()))
                .thenReturn(1L);

        // then
        assertThat(cartService.add(customer.getId(), request)).isOne();
    }

    @DisplayName("이미 동일한 상품이 카트에 등록되어 있을 경우 예외가 발생한다.")
    @Test
    void add_duplicateCartItemExists_throwsException() {
        // given
        final CartSaveServiceRequest request = new CartSaveServiceRequest(1L, 5);

        // when
        when(cartItemDao.existsInCart(any(Long.class), any(Long.class)))
                .thenReturn(true);

        assertThatThrownBy(() -> cartService.add(1L, request))
                .isInstanceOf(AlreadyInCartException.class);
    }

    @DisplayName("회원 장바구니에 존재하는 모든 상품을 불러온다.")
    @Test
    void findAllByCustomerId() {
        // given
        final Cart cart1 = new Cart(1L, 1L, "치킨", 3000, "www.chicken.com", 5);
        final Cart cart2 = new Cart(2L, 2L, "피자", 4000, "www.pizza.com", 5);

        // when
        when(cartItemDao.findAllByCustomerId(1L))
                .thenReturn(List.of(cart1, cart2));

        // then
        assertThat(cartService.findAllByCustomerId(1L)).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new CartServiceResponse(1L, 1L, "치킨", 3000, "www.chicken.com", 5),
                        new CartServiceResponse(2L, 2L, "피자", 4000, "www.pizza.com", 5)
                ));
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 변경한다.")
    @Test
    void updateQuantity() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));
        final Product product = new Product(1L, "치킨", 3000, "www.chicken.com");
        final Cart cart = new Cart(1L, 1L, "치킨", 3000, "www.chicken.com", 5);

        // when
        when(cartItemDao.findCartByCustomerIdAndProductId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.of(cart));
        cartService.updateQuantity(customer.getId(), new CartUpdateServiceRequest(product.getId(), 1000));

        // then
        verify(cartItemDao, times(1)).update(cart, customer.getId());
    }

    @DisplayName("장바구니에서 품목들을 삭제한다.")
    @Test
    void delete() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));

        // when
        when(cartItemDao.findIdsByCustomerId(any(Long.class)))
                .thenReturn(List.of(1L, 2L));

        cartService.delete(customer.getId(), new CartDeleteServiceRequest(List.of(1L, 2L)));

        // then
        verify(cartItemDao, times(1)).deleteAllById(List.of(1L, 2L));
    }
}
