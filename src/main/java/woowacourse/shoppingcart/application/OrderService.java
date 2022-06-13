package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao, final CustomerDao customerDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    public Long addOrder(final List<OrderRequest> orderDetailRequests, final Long customerId) {
        final Customer customer = getCustomer(customerId);
        final Long ordersId = orderDao.addOrders(customer.getId());

        for (final OrderRequest orderDetail : orderDetailRequests) {
            final Long cartId = orderDetail.getCartId();
            final Long productId = cartItemDao.findProductIdById(cartId);
            final int quantity = orderDetail.getQuantity();

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItem(cartId);
        }

        return ordersId;
    }

    @Transactional(readOnly = true)
    public OrdersResponse findOrderById(final Long customerId, final Long orderId) {
        validateOrderIdByCustomerName(customerId, orderId);
        final Orders orders = findOrderResponseDtoByOrderId(orderId);
        return new OrdersResponse(orders);
    }

    private void validateOrderIdByCustomerName(final Long customerId, final Long orderId) {
        final Customer customer = getCustomer(customerId);
        if (!orderDao.isValidOrderId(customer.getId(), orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<OrdersResponse> findOrdersByCustomerId(final Long customerId) {
        final Customer customer = getCustomer(customerId);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customer.getId());

        final List<Orders> ordersByCustomerId = orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
        return ordersByCustomerId.stream()
                .map(OrdersResponse::new)
                .collect(Collectors.toList());
    }

    private Customer getCustomer(final Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
    }

    private Orders findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetail> orderDetails = ordersDetailDao.findOrdersDetailsJoinProductByOrderId(orderId);
        return new Orders(orderId, orderDetails);
    }
}
