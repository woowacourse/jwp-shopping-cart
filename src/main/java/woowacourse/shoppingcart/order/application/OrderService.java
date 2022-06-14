package woowacourse.shoppingcart.order.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.cart.application.CartService;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.order.dao.OrderDao;
import woowacourse.shoppingcart.order.dao.OrdersDetailDao;
import woowacourse.shoppingcart.order.domain.OrderDetail;
import woowacourse.shoppingcart.order.domain.Orders;
import woowacourse.shoppingcart.order.dto.OrderCreationRequest;
import woowacourse.shoppingcart.order.exception.notfound.NotFoundOrderException;
import woowacourse.shoppingcart.product.dao.ProductDao;
import woowacourse.shoppingcart.product.domain.Product;

@Service
@Transactional
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final ProductDao productDao;
    private final CartService cartService;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final ProductDao productDao, final CartService cartService) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
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
            cartService.deleteCartBy(customer, productId);
        }

        return orderId;
    }

    public Orders findOrderById(final Customer customer, final Long orderId) {
        validateOrderIdByCustomerName(customer.getId(), orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(final Long customerId, final Long orderId) {
        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new NotFoundOrderException();
        }
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

    public List<Orders> findAllOrders(final Customer customer) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customer.getId());

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }
}
