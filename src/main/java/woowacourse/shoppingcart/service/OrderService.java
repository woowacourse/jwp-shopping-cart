package woowacourse.shoppingcart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.CreateOrderDetailRequest;
import woowacourse.shoppingcart.dto.response.OrderDetailResponse;
import woowacourse.shoppingcart.dto.response.OrderResponse;
import woowacourse.shoppingcart.exception.notfound.NotFoundOrderException;

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

    public Long addOrder(final List<CreateOrderDetailRequest> orderDetailRequests, final UserName customerName) {
        final Long customerId = customerDao.getIdByUserName(customerName);
        final Long ordersId = orderDao.addOrders(customerId);

        for (final CreateOrderDetailRequest orderDetail : orderDetailRequests) {
            final Long cartId = orderDetail.getCartId();
            final Long productId = cartItemDao.getProductIdById(cartId);
            final int quantity = orderDetail.getQuantity();

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItem(cartId);
        }

        return ordersId;
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrderById(final UserName customerName, final Long orderId) {
        validateOrderIdByCustomerName(customerName, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(final UserName customerName, final Long orderId) {
        final Long customerId = customerDao.getIdByUserName(customerName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new NotFoundOrderException();
        }
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findOrdersByCustomerName(final UserName customerName) {
        final Long customerId = customerDao.getIdByUserName(customerName);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }

    private OrderResponse findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetailResponse> ordersDetails = new ArrayList<>();
        for (final OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.getProductById(productQuantity.getProductId());
            final int quantity = productQuantity.getQuantity();
            ordersDetails.add(new OrderDetailResponse(product, quantity));
        }
        return new OrderResponse(orderId, ordersDetails);
    }
}
