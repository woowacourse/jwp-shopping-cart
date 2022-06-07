package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.InvalidProductException;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartItemDao cartItemDao;
    @Mock
    private CustomerDao customerDao;
    @Mock
    private ProductDao productDao;

    @Test
    @DisplayName("장바구니에 상품을 담다.")
    void addCart() {
        //given
        final long expected = 1L;
        when(cartItemDao.addCartItem(any(Long.class), any(Long.class)))
                .thenReturn(expected);

        //when
        final Long actual = cartService.addCart(1L, 1L);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("장바구니에 상품을 담믈 때 고객이나 상품이 존재하지 않을 경우 예외를 던진다.")
    void addCart_invalidCustomerOrProduct_throwsException() {
        //given
        when(cartItemDao.addCartItem(any(Long.class), any(Long.class)))
                .thenThrow(DataIntegrityViolationException.class);

        //when, then
        assertThatThrownBy(() -> cartService.addCart(1L, 1L))
                .isInstanceOf(InvalidProductException.class);
    }

    @Test
    @DisplayName("고객 id 에 해당하는 장바구니 상품들을 조회한다.")
    void findCartsByCustomerId() {
        //given
        final long customerId = 1L;
        final List<Cart> expected = List.of(new Cart(1L, 2L, "카레", 1000, 3, "www.na/e"));
        when(cartItemDao.findProductsByCustomerId(customerId))
                .thenReturn(expected);

        //when
        final List<Cart> actual = cartService.findCartsByCustomerId(customerId);

        //then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
