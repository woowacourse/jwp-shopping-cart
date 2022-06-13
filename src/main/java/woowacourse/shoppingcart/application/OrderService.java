package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.OrderNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao, final CustomerDao customerDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    @Transactional
    public Long addOrder(final Long customerId, final List<OrderRequest> orderRequests) {
        validateCustomerExists(customerId);
        final Long ordersId = orderDao.addOrders(customerId);
        for (final OrderRequest orderRequest : orderRequests) {
            final Long cartId = orderRequest.getCartId();
            final Long productId = cartItemDao.findProductIdById(cartId);
            ordersDetailDao.addOrdersDetail(ordersId, productId, orderRequest.getQuantity());
            cartItemDao.deleteCartItem(cartId);
        }

        return ordersId;
    }

    public OrderResponse findOrderById(final Long customerId, final Long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        final Orders orders = getOrders(orderId);

        return OrderResponse.from(orders);
    }

    public List<OrderResponse> findOrdersByCustomerId(final Long customerId) {
        validateCustomerExists(customerId);
        validateCustomerOrderExists(customerId);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        final List<Orders> orders = orderIds.stream()
                .map(this::getOrders)
                .collect(Collectors.toList());

        return orders.stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    private void validateCustomerExists(final Long customerId) {
        customerDao.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
    }

    private void validateOrderIdByCustomerId(final Long customerId, final Long orderId) {
        validateCustomerExists(customerId);
        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("주문 내역이 존재하지 않습니다.");
        }
    }

    private void validateCustomerOrderExists(final Long customerId) {
        if (!orderDao.existsOrderByCustomerId(customerId)) {
            throw new OrderNotFoundException();
        }
    }

    private Orders getOrders(final Long orderId) {
        final List<OrderDetail> orderDetails = ordersDetailDao.findAllByOrderId(orderId);
        return new Orders(orderId, orderDetails);
    }
}
