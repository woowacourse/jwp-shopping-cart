package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.dto.OrderDetailRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final CartItemDao cartItemDao;

    public OrderService(final OrderDao orderDao, final OrderDetailDao orderDetailDao,
                        final CartItemDao cartItemDao) {
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.cartItemDao = cartItemDao;
    }

    public long addOrder(final OrderRequest ordersRequest, final long customerId) {
        final long ordersId = orderDao.addOrders(customerId);

        for (final OrderDetailRequest orderDetail : ordersRequest.getOrder()) {
            final long productId = orderDetail.getId();
            final int quantity = orderDetail.getQuantity();
            final long cartId = cartItemDao.findIdByCustomerIdAndProductId(customerId, productId);

            orderDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteById(cartId);
        }

        return ordersId;
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(final long customerId, final Long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        final Order order = findOrderById(orderId);
        return OrderResponse.of(order);
    }

    private void validateOrderIdByCustomerId(final long customerId, final Long orderId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);
        orderIds.stream()
                .filter(it -> it.equals(orderId))
                .findFirst()
                .orElseThrow(() -> new InvalidOrderException("유저에게는 해당 order_id가 없습니다."));
    }

    @Transactional(readOnly = true)
    public OrdersResponse findOrdersByCustomerId(final long customerId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        final List<Order> orders = orderIds.stream()
                .map(this::findOrderById)
                .collect(Collectors.toList());

        return OrdersResponse.of(orders);
    }

    private Order findOrderById(final long orderId) {
        return new Order(orderId, orderDetailDao.findOrderDetailsByOrderId(orderId));
    }
}
