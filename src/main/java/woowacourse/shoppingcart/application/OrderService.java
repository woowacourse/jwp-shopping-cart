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
import woowacourse.shoppingcart.entity.OrderDetailEntity;
import woowacourse.shoppingcart.exception.InvalidOrderException;

import java.util.List;
import java.util.Optional;
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
        orderDetailDao.saveAll(convertOrderDetailEntities(ordersRequest, ordersId));

        final List<Long> productIds = extractProductIds(ordersRequest);
        final List<Long> cartIds = cartItemDao.findIdsByCustomerIdAndProductIds(customerId, productIds);
        cartItemDao.deleteAll(cartIds);

        return ordersId;
    }

    private List<OrderDetailEntity> convertOrderDetailEntities(final OrderRequest ordersRequest, final long ordersId) {
        return ordersRequest.getOrder()
                .stream()
                .map(it -> new OrderDetailEntity(ordersId, it.getProductId(), it.getQuantity()))
                .collect(Collectors.toList());
    }

    private List<Long> extractProductIds(final OrderRequest ordersRequest) {
        return ordersRequest.getOrder()
                .stream()
                .map(OrderDetailRequest::getProductId)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(final long customerId, final long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        final Order order = findOrderById(orderId);
        return OrderResponse.of(order);
    }

    private void validateOrderIdByCustomerId(final long customerId, final long orderId) {
        final Optional<Long> ordersId = orderDao.findOrderIdByOrderIdAndCustomerId(orderId, customerId);
        if(ordersId.isEmpty()) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
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
