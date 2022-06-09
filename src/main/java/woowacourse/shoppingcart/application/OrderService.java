package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.OrderNotFoundException;

@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class OrderService {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderService(final OrderDao orderDao, final OrderDetailDao orderDetailDao,
            final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public Long addOrder(final List<OrderRequest> orderDetailRequests, final String customerName) {
        final Long customerId = customerDao.findIdByName(customerName);
        final Long orderId = orderDao.addOrders(customerId);

        addOrderDetail(orderDetailRequests, orderId);

        return orderId;
    }

    private void addOrderDetail(final List<OrderRequest> orderDetailRequests, final Long orderId) {
        for (final OrderRequest orderDetailRequest : orderDetailRequests) {
            final Long cartId = orderDetailRequest.getCartId();
            final Quantity quantity = new Quantity(orderDetailRequest.getQuantity());
            final Product product = productDao.findProductById(cartItemDao.findProductIdById(cartId));
            final OrderDetail orderDetail = new OrderDetail(product, quantity);

            orderDetailDao.addOrdersDetail(orderId, orderDetail);
            cartItemDao.deleteCartItem(cartId);
        }
    }

    public OrderResponse findOrderById(final String customerName, final Long orderId) {
        validateOrderIdByCustomerName(customerName, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(final String customerName, final Long orderId) {
        final Long customerId = customerDao.findIdByName(customerName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new OrderNotFoundException();
        }
    }

    public List<OrderResponse> findOrdersByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByName(customerName);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }

    private OrderResponse findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetail> orderDetails = orderDetailDao.findOrdersDetailsByOrderId(orderId);
        return new OrderResponse(orderId, toOrderDetailResponses(orderDetails));
    }

    private List<OrderDetailResponse> toOrderDetailResponses(final List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toList());
    }
}
