package woowacourse.shoppingcart.order.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.dao.CartItemDao;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.customer.dao.CustomerDao;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.order.dao.OrderDao;
import woowacourse.shoppingcart.order.dao.OrdersDetailDao;
import woowacourse.shoppingcart.order.domain.OrderDetail;
import woowacourse.shoppingcart.order.domain.Orders;
import woowacourse.shoppingcart.order.dto.OrderCreationRequest;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.product.domain.Product;

@Service
@Transactional
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final CartService cartService;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao, final CustomerDao customerDao,
                        final ProductDao productDao, final CartService cartService) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
        this.cartService = cartService;
    }

    public Long addOrder(final List<OrderCreationRequest> requests, final Customer customer) {
        final Cart cart = cartService.findCartBy(customer);
        final Long orderId = orderDao.addOrders(customer.getId());

        for (final OrderCreationRequest orderDetail : requests) {
            final Long productId = orderDetail.getProductId();
            final CartItem cartItem = cart.getItemById(productId);

            ordersDetailDao.addOrdersDetail(orderId, cartItem.getProduct().getId(), cartItem.getQuantity());
            cartItemDao.deleteCartItem(cartItem.getId());
        }

        return orderId;
    }

    public Orders findOrderById(final String customerName, final Long orderId) {
        validateOrderIdByCustomerName(customerName, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(final String customerName, final Long orderId) {
        final Long customerId = customerDao.findIdByUserName(customerName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<Orders> findOrdersByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(orderId -> findOrderResponseDtoByOrderId(orderId))
                .collect(Collectors.toList());
    }

    private Orders findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetail> ordersDetails = new ArrayList<>();
        for (final OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.findProductById(productQuantity.getProductId());
            final int quantity = productQuantity.getQuantity();
            ordersDetails.add(new OrderDetail(product, quantity));
        }

        return new Orders(orderId, ordersDetails);
    }
}
