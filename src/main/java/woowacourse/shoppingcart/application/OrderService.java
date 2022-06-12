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
import woowacourse.shoppingcart.domain.customer.Customer;
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

    public Long save(final List<OrderRequest> orderDetailRequests, final Customer customer) {
        final Long orderId = orderDao.save(customer.getId());

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

    public OrderResponse findOrderByCustomer(final Customer customer, final Long orderId) {
        validateOrderIdByCustomer(customer, orderId);
        List<OrderDetail> orderDetails = ordersDetailDao.findOrderDetailsByOrderId(orderId);
        return new OrderResponse(orderId, orderDetails);
    }

    private void validateOrderIdByCustomer(final Customer customer, final Long orderId) {
        if (!orderDao.isValidOrderId(customer.getId(), orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public OrdersResponse findOrdersByCustomer(final Customer customer) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customer.getId());

        return new OrdersResponse(orderIds.stream()
                .map(orderId -> findOrderByCustomer(customer, orderId))
                .collect(Collectors.toList()));
    }

}
