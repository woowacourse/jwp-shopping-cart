package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.OrderDetailServiceResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundOrderException;
import woowacourse.shoppingcart.exception.NotFoundProductException;
import woowacourse.shoppingcart.ui.order.dto.request.OrderRequest;

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

    public Long addOrder(final Long customerId, final List<OrderRequest> orderDetailRequests) {
        final Long ordersId = orderDao.addOrders(customerId);

        for (final OrderRequest orderDetail : orderDetailRequests) {
            final Long cartId = orderDetail.getCartId();
            final Long productId = cartItemDao.findProductIdById(cartId)
                    .orElseThrow(NotFoundProductException::new);
            final int quantity = orderDetail.getQuantity();

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItem(cartId);
        }

        return ordersId;
    }

    public OrderDetailServiceResponse findOrderById(final Long customerId, final Long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        final Orders orders = findOrderResponseDtoByOrderId(orderId);
        final int totalPrice = orders.calculateTotalPrice();
        return OrderDetailServiceResponse.from(orders, totalPrice);
    }

    private void validateOrderIdByCustomerId(final Long customerId, final Long orderId) {
        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new NotFoundOrderException();
        }
    }

    public List<Orders> findOrdersByCustomerId(final Long customerId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }

    private Orders findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetail> ordersDetails = new ArrayList<>();
        for (final OrderDetail orderDetail : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.findProductById(orderDetail.getProductId())
                    .orElseThrow(NotFoundProductException::new);
            final int quantity = orderDetail.getQuantity();
            ordersDetails.add(new OrderDetail(product, quantity));
        }

        return new Orders(orderId, ordersDetails);
    }
}
