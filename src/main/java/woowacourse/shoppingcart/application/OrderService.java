package woowacourse.shoppingcart.application;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
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

    public Long addOrder(final List<OrderRequest> orderRequests, final Long customerId) {
        final Customer customer = getCustomer(customerId);
        final Long ordersId = orderDao.addOrders(customer.getId());

        final List<Cart> carts = orderRequests.stream()
                .map(this::toCartByOrderRequest)
                .collect(Collectors.toList());
        ordersDetailDao.batchAddOrdersDetail(ordersId, carts);

        final List<Long> cartIds = carts.stream()
                .map(Cart::getId)
                .collect(Collectors.toList());
        cartItemDao.batchDeleteCartItem(cartIds);
        return ordersId;
    }

    private Cart toCartByOrderRequest(final OrderRequest orderRequest) {
        final Cart cart = cartItemDao.findJoinProductById(orderRequest.getCartId())
                .orElseThrow(InvalidCartItemException::new);
        cart.checkQuantity(orderRequest.getQuantity());
        return cart;
    }

    @Transactional(readOnly = true)
    public OrdersResponse findOrderById(final Long customerId, final Long orderId) {
        validateOrderIdByCustomerName(customerId, orderId);
        final List<OrderDetail> orderDetails = ordersDetailDao.findOrdersDetailsJoinProductByOrderId(orderId);
        final Orders orders = new Orders(orderId, orderDetails);
        return new OrdersResponse(orders);
    }
    
    private void validateOrderIdByCustomerName(final Long customerId, final Long orderId) {
        final Customer customer = getCustomer(customerId);
        if (!orderDao.isValidOrderId(customer.getId(), orderId)) {
            throw new InvalidOrderException("해당 유저가 주문하지 않은 항목입니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<OrdersResponse> findOrdersByCustomerId(final Long customerId) {
        final Customer customer = getCustomer(customerId);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customer.getId());
        final List<OrderDetail> orderDetails = ordersDetailDao.findAllJoinProductByOrderIds(orderIds);
        final Map<Long, List<OrderDetail>> orderDetailsByOrderId = orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetail::getOrderId, mapping(Function.identity(), toList())));

        return orderDetailsByOrderId.entrySet().stream()
                .map(entry -> new Orders(entry.getKey(), entry.getValue()))
                .map(OrdersResponse::new)
                .collect(Collectors.toList());
    }

    private Customer getCustomer(final Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
    }
}
