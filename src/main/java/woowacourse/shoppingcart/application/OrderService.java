package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.order.OrderResponse;
import woowacourse.shoppingcart.dto.order.OrdersResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public Long save(final List<OrderRequest> orderDetailRequests, final Long customerId) {
        final Long orderId = orderDao.save(customerId);

        for (final OrderRequest request : orderDetailRequests) {
            saveOrderThenRemoveCartItem(orderId, request, customerId);
        }

        return orderId;
    }

    private void saveOrderThenRemoveCartItem(Long orderId, OrderRequest request, Long customerId) {
        CartItem cartItem = cartItemDao.findById(request.getCartItemId());
        OrderDetail orderDetail = OrderDetail.from(cartItem);
        reduceProductStock(orderDetail);
        ordersDetailDao.save(orderId, orderDetail);
        cartItemDao.deleteById(cartItem.getId(), customerId);
    }

    private void reduceProductStock(OrderDetail orderDetail) {
        Product product = productDao.findProductById(orderDetail.getProductId());
        product.reduceStock(orderDetail.getQuantity());
        productDao.updateStock(product);
    }

    public OrderResponse findOrderByCustomerId(final Long customerId, final Long orderId) {
        List<OrderDetail> orderDetails = ordersDetailDao.findOrderDetailsByOrderIdAndCustomerId(orderId, customerId);
        return new OrderResponse(orderId, orderDetails);
    }

    public OrdersResponse findOrdersByCustomerId(final Long customerId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);
        List<OrderDetail> orderDetails = ordersDetailDao.findAllByCustomerId(customerId);

        return new OrdersResponse(collectOrderDetailsToOrder(orderDetails));
    }

    private List<OrderResponse> collectOrderDetailsToOrder(List<OrderDetail> orderDetails) {
        Map<Long, List<OrderDetail>> groupedOrderDetails = orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetail::getOrderId));

        return groupedOrderDetails.keySet().stream()
                .map(orderId -> new OrderResponse(orderId, groupedOrderDetails.get(orderId)))
                .collect(Collectors.toList());
    }

}
