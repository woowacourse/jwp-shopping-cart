package woowacourse.shoppingcart.order.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.auth.support.exception.AuthException;
import woowacourse.shoppingcart.auth.support.exception.AuthExceptionCode;
import woowacourse.shoppingcart.cart.domain.Cart;
import woowacourse.shoppingcart.cart.support.exception.CartException;
import woowacourse.shoppingcart.cart.support.exception.CartExceptionCode;
import woowacourse.shoppingcart.cart.support.jdbc.dao.CartItemDao;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.support.jdbc.dao.CustomerDao;
import woowacourse.shoppingcart.order.application.dto.request.OrderRequest;
import woowacourse.shoppingcart.order.application.dto.response.OrderDetailResponse;
import woowacourse.shoppingcart.order.application.dto.response.OrderResponse;
import woowacourse.shoppingcart.order.domain.OrderDetail;
import woowacourse.shoppingcart.order.support.exception.OrderException;
import woowacourse.shoppingcart.order.support.exception.OrderExceptionCode;
import woowacourse.shoppingcart.order.support.jdbc.dao.OrderDao;
import woowacourse.shoppingcart.order.support.jdbc.dao.OrdersDetailDao;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.support.exception.ProductException;
import woowacourse.shoppingcart.product.support.exception.ProductExceptionCode;
import woowacourse.shoppingcart.product.support.jdbc.dao.ProductDao;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
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
    public long addOrder(final long customerId, final OrderRequest orderRequest) {
        final Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.REQUIRED_AUTHORIZATION));
        final long orderId = orderDao.addOrders(customer.getId(), LocalDateTime.now());

        final List<Cart> cartItems = cartItemDao.findAllByCustomerId(customer.getId());

        for (final long productId : orderRequest.getProductIds()) {
            final Product product = productDao.findById(productId)
                    .orElseThrow(() -> new ProductException(ProductExceptionCode.NO_SUCH_PRODUCT_EXIST));

            final Cart cart = cartItems.stream()
                    .filter(it -> it.getProductId() == productId)
                    .findAny()
                    .orElseThrow(() -> new CartException(CartExceptionCode.NO_SUCH_PRODUCT_EXIST));

            ordersDetailDao.addOrdersDetail(new OrderDetail(orderId, product.getId(), cart.getQuantity()));
            cartItemDao.deleteCartItem(cart.getId());
        }

        return orderId;
    }

    public OrderResponse findOrderById(final long customerId, final long orderId) {
        validateOrderAccessable(customerId, orderId);

        final Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.REQUIRED_AUTHORIZATION));

        final List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for (final OrderDetail orderDetail : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.findById(orderDetail.getProductId())
                    .orElseThrow(() -> new ProductException(ProductExceptionCode.NO_SUCH_PRODUCT_EXIST));
            orderDetailResponses.add(OrderDetailResponse.of(product, orderDetail));
        }

        return new OrderResponse(orderId, orderDetailResponses, calculateTotalPrice(orderDetailResponses),
                orderDao.getOrderDateById(orderId));
    }

    private void validateOrderAccessable(final long customerId, final long orderId) {
        if (!orderDao.existsById(orderId)) {
            throw new OrderException(OrderExceptionCode.NO_SUCH_ORDER_EXIST);
        }
        if (!orderDao.existsByIdAndCustomerId(orderId, customerId)) {
            throw new AuthException(AuthExceptionCode.REQUIRED_AUTHORIZATION);

        }
    }

    private long calculateTotalPrice(final List<OrderDetailResponse> orderDetailResponses) {
        return orderDetailResponses.stream()
                .mapToLong(orderDetailResponse -> orderDetailResponse.getPrice() * orderDetailResponse.getQuantity())
                .sum();
    }
}
