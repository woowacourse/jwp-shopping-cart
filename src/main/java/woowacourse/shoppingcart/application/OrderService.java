package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.order.OrderResponse;
import woowacourse.shoppingcart.dto.order.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long save(final List<OrderRequest> orderDetailRequests, final String username) {
        final Long customerId = customerDao.findIdByUserName(username);
        final Long orderId = orderDao.save(customerId);

        for (final OrderRequest request : orderDetailRequests) {
            saveOrderThenRemoveCartItem(orderId, request);
        }

        return orderId;
    }

    private void saveOrderThenRemoveCartItem(Long orderId, OrderRequest request) {
        CartItem cartItem = cartItemDao.findById(request.getCartItemId());
        OrderDetail orderDetail = OrderDetail.from(cartItem);
        reduceProductStock(orderDetail);
        ordersDetailDao.save(orderId, orderDetail);
        cartItemDao.deleteById(cartItem.getId());
    }

    private void reduceProductStock(OrderDetail orderDetail) {
        Product product = productDao.findProductById(orderDetail.getProductId());
        product.reduceStock(orderDetail.getQuantity());
        productDao.updateStock(product);
    }

    public OrderResponse findOrderById(final String username, final Long orderId) {
        validateOrderIdByUsername(username, orderId);
        List<OrderDetail> orderDetails = ordersDetailDao.findOrderDetailsByOrderId(orderId);
        return new OrderResponse(orderId, orderDetails);
    }

    private void validateOrderIdByUsername(final String username, final Long orderId) {
        final Long customerId = customerDao.findIdByUserName(username);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public OrdersResponse findOrdersByUsername(final String username) {
        final Long customerId = customerDao.findIdByUserName(username);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return new OrdersResponse(orderIds.stream()
                .map(orderId -> findOrderById(username, orderId))
                .collect(Collectors.toList()));
    }

}
