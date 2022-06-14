package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;
    @Mock
    private CartItemDao cartItemDao;
    @Mock
    private CustomerDao customerDao;

    @Test
    void updateQuantity() {
        Long cartItemId = 1L;
        List<Cart> carts = List.of(
                new Cart(1L, 1L, "이름", 5000, "www.imageuirl1.com", 50),
                new Cart(2L, 2L, "이름2", 4000, "www.imageuirl2.com", 100),
                new Cart(3L, 3L, "이름3", 1000, "www.imageuirl3.com", 10));
        Customer customer = new Customer(1L, "giron", new Password("p!@1Assword"));

        given(cartItemDao.findAllByCustomerId(any())).willReturn(carts);
        doNothing().when(cartItemDao).updateQuantity(cartItemId, 30);
        given(customerDao.findIdByUserName(any())).willReturn(1L);
        assertDoesNotThrow(() -> cartService.updateQuantity(customer.getUserName(), cartItemId, 30));
        verify(cartItemDao).findAllByCustomerId(any());
        verify(customerDao).findIdByUserName(any());
    }
}
