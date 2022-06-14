package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.InvalidAccessException;
import woowacourse.exception.InvalidOrderException;
import woowacourse.exception.InvalidProductException;
import woowacourse.exception.InvalidTokenException;
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
    public OrderResponse addOrder(final long customerId, final OrderRequest orderRequest) {
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

    public OrderResponse findOrderById(final long customerId, final long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        Orders order = getByOrderId(orderId);
        return findOrderResponse(order);
    }

    private Orders getByOrderId(long orderId) {
        return orderDao.findById(orderId)
                .orElseThrow(InvalidOrderException::new);
    }

    private CartItem findCartItem(List<CartItem> cartItems, long productId) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(NotInCustomerCartItemException::new);
    }

    private Customer getByCustomerId(long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidTokenException::new);
    }

    private void validateOrderIdByCustomerId(final long customerId, final long orderId) {
        Customer customer = getByCustomerId(customerId);

        if (!orderDao.isValidOrderId(customer.getId(), orderId)) {
            throw new InvalidAccessException();
        }
    }

    private OrderResponse findOrderResponse(final Orders order) {
        final List<OrderDetailResponse> ordersDetailResponses = new ArrayList<>();
        List<OrderDetail> ordersDetails = ordersDetailDao.findOrdersDetailsByOrderId(order.getId());
        for (final OrderDetail orderDetail : ordersDetails) {
            final Product product = productDao.findProductById(orderDetail.getProductId())
                    .orElseThrow(InvalidProductException::new);
            ordersDetailResponses.add(new OrderDetailResponse(product.getId(), product.getName(),
                    orderDetail.getQuantity(), product.getPrice(), product.getImage()));
        }

        return new OrderResponse(order.getId(), ordersDetailResponses, calculateTotalPrice(ordersDetailResponses),
                order.getDate());
    }

    private int calculateTotalPrice(List<OrderDetailResponse> ordersDetailResponses) {
        return ordersDetailResponses.stream()
                .mapToInt(ordersDetailResponse -> ordersDetailResponse.getQuantity() * ordersDetailResponse.getPrice())
                .sum();
    }
}
