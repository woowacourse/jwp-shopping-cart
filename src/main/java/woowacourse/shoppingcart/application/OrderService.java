package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.domain.CartItemNotFoundException;
import woowacourse.shoppingcart.exception.domain.InvalidOrderException;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class OrderService {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final CartItemDao cartItemDao;

    public OrderService(OrderDao orderDao, OrderDetailDao orderDetailDao, CartItemDao cartItemDao) {
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.cartItemDao = cartItemDao;
    }

    @Transactional
    public Long addOrder(final List<OrderDetailRequest> orderDetailRequests, final Long customerId) {
        Order order = new Order(mapToOrderDetails(orderDetailRequests, customerId));
        return addOrderAndDetails(order, customerId);
    }

    private List<OrderDetail> mapToOrderDetails(List<OrderDetailRequest> orderDetailRequests, Long customerId) {
        final List<CartItem> cartItems = cartItemDao.findCartByCustomerId(customerId).getItems();
        return orderDetailRequests.stream()
            .map(request -> new OrderDetail(request.getQuantity(), findProduct(request.getCartId(), cartItems)))
            .collect(Collectors.toUnmodifiableList());
    }

    private Product findProduct(Long cartId, List<CartItem> cartItems) {
        return cartItems.stream()
            .filter(item -> item.getId().equals(cartId))
            .map(CartItem::getProduct)
            .findAny()
            .orElseThrow(CartItemNotFoundException::new);
    }

    private Long addOrderAndDetails(Order order, Long customerId) {
        final Long orderId = orderDao.addOrder(customerId);
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetailDao.addOrderDetail(orderId, orderDetail);
        }
        return orderId;
    }

    public OrderResponse findOrderById(final Long customerId, final Long orderId) {
        validateOrderIdByCustomerName(customerId, orderId);
        return OrderResponse.from(findOrderById(orderId));
    }

    private void validateOrderIdByCustomerName(final Long customerId, final Long orderId) {
        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

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
