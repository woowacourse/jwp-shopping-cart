package woowacourse.order.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.cartitem.dao.CartItemDao;
import woowacourse.cartitem.domain.CartItem;
import woowacourse.cartitem.exception.InvalidCartItemException;
import woowacourse.customer.dao.CustomerDao;
import woowacourse.order.dao.OrderDao;
import woowacourse.order.dao.OrdersDetailDao;
import woowacourse.order.domain.Orders;
import woowacourse.order.dto.OrderAddRequest;
import woowacourse.order.dto.OrderResponse;
import woowacourse.order.dto.OrderResponses;
import woowacourse.order.exception.InvalidOrderException;

@Transactional(rollbackFor = Exception.class)
@Service
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public OrderService(
        final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
        final CartItemDao cartItemDao, final CustomerDao customerDao
    ) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    public Long addOrder(final String customerName, final List<OrderAddRequest> orderAddRequests) {
        final Long customerId = customerDao.findIdByUsername(customerName);
        final Long orderId = orderDao.save(customerId);

        for (final OrderAddRequest orderAddRequest : orderAddRequests) {
            final CartItem cartItem = cartItemDao.findById(orderAddRequest.getCartItemId())
                .orElseThrow(() -> new InvalidCartItemException("장바구니를 찾을 수 없습니다."));

            ordersDetailDao.save(orderId, cartItem.getProductId(), cartItem.getQuantity().getValue());
            cartItemDao.deleteByIdAndCustomerId(cartItem.getId(), customerId);
        }

        return orderId;
    }

    public OrderResponse findOrderById(final String customerName, final Long orderId) {
        validateOrderIdByCustomerName(customerName, orderId);
        return findOrderResponseByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(final String customerName, final Long orderId) {
        final Long customerId = customerDao.findIdByUsername(customerName);

        if (!orderDao.existsByIdAndCustomerId(customerId, orderId)) {
            throw new InvalidOrderException("해당 주문을 찾을 수 없습니다.");
        }
    }

    public OrderResponses findOrdersByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUsername(customerName);
        final List<Long> orderIds = orderDao.findIdsByCustomerId(customerId);

        return OrderResponses.from(orderIds.stream()
            .map(id -> new Orders(id, ordersDetailDao.findAllByOrderId(id)))
            .collect(Collectors.toList()));
    }

    private OrderResponse findOrderResponseByOrderId(final Long orderId) {
        final Orders orders = new Orders(orderId, ordersDetailDao.findAllByOrderId(orderId));
        return OrderResponse.from(orders);
    }
}
