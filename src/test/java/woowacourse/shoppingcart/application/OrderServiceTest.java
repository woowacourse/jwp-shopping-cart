package woowacourse.shoppingcart.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.ui.order.dto.request.OrderRequest;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrdersDetailDao ordersDetailDao;

    @Mock
    private CartItemDao cartItemDao;

    @Test
    @DisplayName("장바구니 상품들을 주문한다.")
    void addOrder() {
        //given
        final Long customerId = 1L;
        final Long ordersId = 1L;
        final Long cartId = 1L;
        final Long productId = 1L;
        final int quantity = 10;
        final OrderRequest orderRequest = new OrderRequest(cartId, quantity);

        when(orderDao.addOrders(customerId))
                .thenReturn(ordersId);
        when(cartItemDao.findProductIdById(cartId))
                .thenReturn(Optional.of(productId));

        //when, then
        assertThatCode(() -> orderService.addOrder(customerId, List.of(orderRequest)))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("주문시 존재하지 않는 상품일 경우 예외를 던진다.")
    void addOrder_invalidProduct_throwsException() {
        //given
        final Long customerId = 1L;
        final Long ordersId = 1L;
        final Long cartId = 1L;
        final int quantity = 10;
        final OrderRequest orderRequest = new OrderRequest(cartId, quantity);

        when(orderDao.addOrders(customerId))
                .thenReturn(ordersId);
        when(cartItemDao.findProductIdById(cartId))
                .thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> orderService.addOrder(customerId, List.of(orderRequest)))
                .isInstanceOf(InvalidProductException.class);
    }
}