package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
import woowacourse.shoppingcart.dto.CartResponse;
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

    @Test
    void add() {
        // given
        final CartSaveServiceRequest request = new CartSaveServiceRequest(1L, 5);
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));
        final Product product = new Product(1L, "치킨", 3000, "www.chicken.com");

        // when
        when(customerDao.findById(1L))
                .thenReturn(Optional.of(customer));
        when(productDao.findProductById(1L))
                .thenReturn(Optional.of(product));
        when(cartItemDao.addCartItem(customer.getId(), product.getId(), request.getQuantity()))
                .thenReturn(1L);

        // then
        assertThat(cartService.add(customer.getId(), request)).isOne();
    }

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
                        new CartResponse(1L, 1L, "치킨", 3000, "www.chicken.com", 5),
                        new CartResponse(2L, 2L, "피자", 4000, "www.pizza.com", 5)
                ));
    }

    @Test
    void updateQuantity() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));
        final Product product = new Product(1L, "치킨", 3000, "www.chicken.com");
        final Cart cart = new Cart(1L, 1L, "치킨", 3000, "www.chicken.com", 5);

        // when
        when(customerDao.findById(any(Long.class)))
                .thenReturn(Optional.of(customer));
        when(cartItemDao.findCartByCustomerIdAndProductId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.of(cart));
        cartService.updateQuantity(customer.getId(), new CartUpdateServiceRequest(product.getId(), 1000));

        // then
        verify(cartItemDao, times(1)).update(cart, customer.getId());
    }

    @Test
    void delete() {
        // given
        final String password = PasswordEncryptor.encrypt("12345678");
        final Customer customer = new Customer(1L, "썬", new Email("sun@gmail.com"), new EncodedPassword(password));

        // when
        when(customerDao.findById(any(Long.class)))
                .thenReturn(Optional.of(customer));
        when(cartItemDao.findIdsByCustomerId(any(Long.class)))
                .thenReturn(List.of(1L, 2L));

        cartService.delete(customer.getId(), new CartDeleteServiceRequest(List.of(1L, 2L)));

        // then
        verify(cartItemDao, times(1)).deleteAllById(List.of(1L, 2L));
    }
}
