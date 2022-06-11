package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.OrdersRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
    }

    public Long addOrder(final long customerId, final OrdersRequest orderDetailRequests) {
        Long createdOrdersId = orderDao.addOrders(customerId);
        List<Long> productIds = orderDetailRequests.getIds();
        List<Integer> quantities = orderDetailRequests.getQuantities();
        ordersDetailDao.addAllOrdersDetails(createdOrdersId, productIds, quantities);
        cartItemDao.deleteAllCartItems(customerId, productIds);
        return createdOrdersId;
    }

    public OrderResponse findOrderById(final long customerId, final Long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        return toOrderResponse(orderId);
    }

    public OrdersResponse findOrdersByCustomerId(final long customerId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);
        return new OrdersResponse(orderIds.stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList()));
    }

    private void validateOrderIdByCustomerId(final long customerId, final Long orderId) {
        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    private OrderResponse toOrderResponse(Long orderId) {
        List<OrderDetail> ordersDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        return new OrderResponse(orderId,
                ordersDetails.stream()
                        .map(this::toOrderDetailResponse)
                        .collect(Collectors.toList()));
    }

    private OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        return new OrderDetailResponse(
                orderDetail.getProductId(),
                orderDetail.getName(),
                orderDetail.cost(),
                orderDetail.getQuantity(),
                orderDetail.getImageUrl());
    }
}
