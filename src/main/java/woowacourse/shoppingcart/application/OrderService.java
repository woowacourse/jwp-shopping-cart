package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.OrderServiceRequest;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.exception.CartNotFoundException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.ui.dto.OrderDetailResponse;
import woowacourse.shoppingcart.ui.dto.OrdersResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final CartDao cartDao;

    public OrderService(OrderDao orderDao, OrderDetailDao orderDetailDao, CartDao cartDao) {
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.cartDao = cartDao;
    }

    public long addOrder(List<OrderServiceRequest> orderRequests, long memberId) {
        long ordersId = orderDao.addOrders(memberId);

        for (OrderServiceRequest order : orderRequests) {
            long cartId = order.getCartId();
            Cart cart = cartDao.findCartById(cartId)
                    .orElseThrow(CartNotFoundException::new);
            orderDetailDao.save(ordersId, cart.getProductId(), cart.getQuantity());
            cartDao.deleteById(cartId);
        }
        return ordersId;
    }

    public OrdersResponse findOrder(long memberId, long orderId) {
        validateOrderIdByMemberId(memberId, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByMemberId(long memberId, long orderId) {
        if (!orderDao.isExistOrderId(memberId, orderId)) {
            throw new InvalidOrderException();
        }
    }

    public List<OrdersResponse> findOrders(long memberId) {
        List<Long> orderIds = orderDao.findOrderIdsByMemberId(memberId);

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }

    private OrdersResponse findOrderResponseDtoByOrderId(long orderId) {
        List<OrderDetailResponse> orderDetails = orderDetailDao.findOrdersDetailsByOrderId(orderId);
        return new OrdersResponse(orderId, orderDetails);
    }
}
