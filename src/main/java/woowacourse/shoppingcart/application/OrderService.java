package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.exception.InvalidAccessException;
import woowacourse.exception.InvalidCustomerException;
import woowacourse.exception.InvalidOrderException;
import woowacourse.exception.InvalidProductException;
import woowacourse.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CartItemDao;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.OrderDao;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.OrdersDetailDao;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.ProductDao;

@Service
@Transactional(readOnly = true)
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

    @Transactional
    public OrderResponse addOrder(final Long customerId, final OrderRequest orderRequest) {
        Customer customer = getByCustomerId(customerId);
        final Long orderId = orderDao.addOrder(customerId);
        List<CartItem> cartItems = cartItemDao.findByCustomerId(customerId);

        for (final Long productId : orderRequest.getProductIds()) {
            ordersDetailDao.addOrdersDetail(orderId, findCartItem(cartItems, productId));
        }

        List<Long> productIds = orderRequest.getProductIds();
        cartItemDao.deleteCartItemsByCustomerId(customer.getId(), productIds);
        return findOrderById(customerId, orderId);
    }

    public OrderResponse findOrderById(@AuthenticationPrincipal Long customerId, final Long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        Orders order = getByOrderId(orderId);
        return findOrderResponse(order);
    }

    private Orders getByOrderId(Long orderId) {
        return orderDao.findById(orderId)
                .orElseThrow(InvalidOrderException::new);
    }

    private CartItem findCartItem(List<CartItem> cartItems, Long productId) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(NotInCustomerCartItemException::new);
    }

    private Customer getByCustomerId(Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidTokenException::new);
    }

    private void validateOrderIdByCustomerId(final Long customerId, final Long orderId) {
        Customer customer = getByCustomerId(customerId);

        if (!orderDao.isValidOrderId(customer.getId(), orderId)) {
            throw new InvalidAccessException();
        }
    }

    private OrderResponse findOrderResponse(final Orders order) {
        final List<OrderDetailResponse> ordersDetails = new ArrayList<>();
        for (final OrderDetail orderDetail : ordersDetailDao.findOrdersDetailsByOrderId(order.getId())) {
            final Product product = productDao.findProductById(orderDetail.getProductId())
                    .orElseThrow(InvalidProductException::new);
            ordersDetails.add(new OrderDetailResponse(product.getId(), product.getName(),
                    orderDetail.getQuantity(), product.getPrice(), product.getImage()));
        }

        return new OrderResponse(order.getId(), ordersDetails, calculateTotalPrice(ordersDetails),
                order.getDate());
    }

    private int calculateTotalPrice(List<OrderDetailResponse> ordersDetails) {
        return ordersDetails.stream()
                .mapToInt(ordersDetail -> ordersDetail.getQuantity() * ordersDetail.getPrice())
                .sum();
    }
}
