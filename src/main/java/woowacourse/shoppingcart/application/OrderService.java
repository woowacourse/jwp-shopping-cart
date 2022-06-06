package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.exception.domain.InvalidOrderException;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final CartItemDao cartItemDao;

    public OrderService(final OrderDao orderDao, final OrderDetailDao orderDetailDao,
        final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.cartItemDao = cartItemDao;
    }

    public Long addOrder(final List<OrderDetailRequest> orderDetailRequests, final Long customerId) {
        final Long orderId = orderDao.addOrders(customerId);

        for (final OrderDetailRequest request : orderDetailRequests) {
            moveCartItemToOrderDetail(orderId, request.getCartId(), request.getQuantity());
        }

        return orderId;
    }

    private void moveCartItemToOrderDetail(Long orderId, Long cartId, int quantity) {
        orderDetailDao.addOrdersDetail(orderId, cartItemDao.findProductIdById(cartId), quantity);
        cartItemDao.deleteCartItem(cartId);
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(final Long customerId, final Long orderId) {
        validateOrderIdByCustomerName(customerId, orderId);
        return OrderResponse.from(findOrderById(orderId));
    }

    private void validateOrderIdByCustomerName(final Long customerId, final Long orderId) {
        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findOrdersByCustomerId(final Long customerId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
            .map(orderId -> OrderResponse.from(findOrderById(orderId)))
            .collect(Collectors.toList());
    }

    private Order findOrderById(final Long orderId) {
        return new Order(
            orderId,
            orderDetailDao.findOrderDetailsByOrderId(orderId)
        );
    }
}
