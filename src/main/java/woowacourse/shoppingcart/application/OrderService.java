package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartsDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final ProductDao productDao;
    private final CartsDao cartsDao;

    public OrderService(final OrderDao orderDao,
                        final OrdersDetailDao ordersDetailDao,
                        final ProductDao productDao,
                        final CartsDao cartsDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.productDao = productDao;
        this.cartsDao = cartsDao;
    }

    public Long addOrder(final Long memberId, final List<OrderRequest> orderRequests) {
        final Long orderId = orderDao.save(memberId);
        final List<Long> cartIds = orderRequests.stream()
                .map(OrderRequest::getCartId)
                .collect(Collectors.toList());
        final List<Cart> cart = cartsDao.findCartsByIds(cartIds);
        ordersDetailDao.addBatchOrderDetails(toOrderDetails(orderId, cart));

        return orderId;
    }

    private List<OrderDetail> toOrderDetails(final Long orderId, final List<Cart> carts) {
        return carts.stream()
                .map(cart -> new OrderDetail(orderId, cart.getProduct().getId(), cart.getQuantity()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(final Long memberId, final Long orderId) {
        validateOrderIdByMemberId(memberId, orderId);
        final Orders foundOrder = orderDao.findOrderByIdLazyOrderDetails(orderId);
        return new OrderResponse(foundOrder.getId(), toOrderDetailResponses(foundOrder.getOrderDetails()));
    }

    private List<OrderDetailResponse> toOrderDetailResponses(final List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(detail -> OrderDetailResponse.from(detail, productDao.findProductById(detail.getProductId())))
                .collect(Collectors.toList());
    }

    private void validateOrderIdByMemberId(final Long memberId, final Long orderId) {
        if (!orderDao.isValidOrderId(memberId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findOrdersByMemberId(final Long memberId) {
        final List<Orders> orders = orderDao.findOrdersByIdLazyOrderDetails(memberId);
        return orders.stream()
                .map(order -> new OrderResponse(order.getId(),
                        toOrderDetailResponses(order.getOrderDetails())))
                .collect(Collectors.toList());
    }
}
