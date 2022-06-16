package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.*;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.OrderQuantityInfo;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.exception.InvalidOrderException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrdersDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderService(final OrdersDao orderDao, final OrderDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long addOrder(final String userName, final List<OrderRequest> orderDetailRequests) {
        final Long customerId = customerDao.findIdByUserName(userName);
        final Long ordersId = orderDao.addOrders(customerId);

        for (final OrderRequest orderDetail : orderDetailRequests) {
            final Long cartId = orderDetail.getCartId();
            final Long productId = cartItemDao.findProductIdById(cartId);
            final int quantity = orderDetail.getQuantity();

            orderDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItem(cartId);
        }

        return ordersId;
    }

    @Transactional(readOnly = true)
    public Orders findOrderById(final String userName, final Long orderId) {
        validateOrderIdByCustomerName(userName, orderId);
        return findOrderById(orderId);
    }

    private void validateOrderIdByCustomerName(final String userName, final Long orderId) {
        final Long customerId = customerDao.findIdByUserName(userName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<Orders> findOrders(final String userName) {
        final Long customerId = customerDao.findIdByUserName(userName);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(this::findOrderById)
                .collect(Collectors.toList());
    }

    private Orders findOrderById(final Long orderId) {
        List<OrderDetail> orderDetails = orderDetailDao.findOrderQuantityInfoByOrderId(orderId)
                .stream()
                .map(this::assembleOrderDetail)
                .collect(Collectors.toList());

        return new Orders(orderId, orderDetails);
    }

    private OrderDetail assembleOrderDetail(OrderQuantityInfo info){
        Product product = productDao.findProductById(info.getProductId());
        return new OrderDetail(product, info.getQuantity());
    }
}
