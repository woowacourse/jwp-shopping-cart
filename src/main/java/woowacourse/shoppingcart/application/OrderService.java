package woowacourse.shoppingcart.application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

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

    public Long addOrder(List<Long> cartItemIds, String email) {
        final Customer customer = customerDao.findByEmail(email);

        final Long ordersId = orderDao.addOrders(customer.getId());
        final List<CartItem> cartItems = cartItemDao.findByIds(cartItemIds);

        for (CartItem cartItem : cartItems) {
            ordersDetailDao.addOrdersDetail(ordersId, cartItem.getProduct().getId(), cartItem.getQuantity());
            cartItemDao.deleteCartItem(cartItem.getId());
        }

        return ordersId;
    }

    private void validateCustomerCart(List<Long> cartItemIds, Long customerId) {
        final Set<Long> savedCartItemIds = new HashSet<>(cartItemDao.findIdsByCustomerId(customerId));
        if (containsAllIds(cartItemIds, savedCartItemIds)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private boolean containsAllIds(List<Long> cartItemIds, Set<Long> savedCartItemIds) {
        return cartItemIds.stream()
                .filter(it -> savedCartItemIds.contains(it))
                .limit(savedCartItemIds.size())
                .count() == savedCartItemIds.size();
    }
//
//    public Orders findOrderById(final String customerName, final Long orderId) {
//        validateOrderIdByCustomerName(customerName, orderId);
//        return findOrderResponseDtoByOrderId(orderId);
//    }

    private void validateOrderIdByCustomerName(final String customerName, final Long orderId) {
        final Long customerId = customerDao.findIdByUserName(customerName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

//    public List<Orders> findOrdersByCustomerName(final String customerName) {
//        final Long customerId = customerDao.findIdByUserName(customerName);
//        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);
//
//        return orderIds.stream()
//                .map(orderId -> findOrderResponseDtoByOrderId(orderId))
//                .collect(Collectors.toList());
//    }

//    private Orders findOrderResponseDtoByOrderId(final Long orderId) {
//        final List<OrderDetail> ordersDetails = new ArrayList<>();
//        for (final OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
//            final Product product = productDao.findProductById(productQuantity.getProductId());
//            final int quantity = productQuantity.getQuantity();
//            ordersDetails.add(new OrderDetail(product, quantity));
//        }
//
//        return new Orders(orderId, ordersDetails);
//    }

    public List<OrderResponse> findOrdersByEmail(String email) {
        final Customer customer = customerDao.findByEmail(email);
        List<Long> ordersIds = orderDao.findOrderIdsByCustomerId(customer.getId());
        return ordersIds.stream()
                .map(this::makeOrderResponseByOrderId)
                .collect(Collectors.toUnmodifiableList());
    }

    private OrderResponse makeOrderResponseByOrderId(Long orderId) {
        List<OrderDetail> orderDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        final List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(OrderDetailResponse::of)
                .collect(Collectors.toUnmodifiableList());
        return new OrderResponse(orderId, orderDetailResponses);
    }
}
