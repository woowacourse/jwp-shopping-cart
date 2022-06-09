package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.order.OrderResponse;
import woowacourse.shoppingcart.dto.order.OrderSaveRequest;
import woowacourse.shoppingcart.dto.order.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

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
    public Long addOrder(final List<OrderSaveRequest> orderSaveRequests, final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        final Long ordersId = orderDao.addOrders(customerId);

        for (final OrderSaveRequest orderSaveRequest : orderSaveRequests) {
            final CartItem cartItem = cartItemDao.findById(orderSaveRequest.getCartItemId());
            purchaseProductAndUpdate(cartItem);
            addOrdersDetails(ordersId, cartItem);
            cartItemDao.deleteCartItem(cartItem.getId());
        }

        return ordersId;
    }

    private void purchaseProductAndUpdate(final CartItem cartItem) {
        final Product purchaseProduct = productDao.findProductById(cartItem.getProductId())
                .purchaseProduct(cartItem.getQuantity());
        productDao.updateProductStock(purchaseProduct);
    }

    private void addOrdersDetails(final Long ordersId, final CartItem cartItem) {
        final Long productId = cartItem.getProductId();
        final int quantity = cartItem.getQuantity();
        ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
    }

    public OrderResponse findOrderById(final String customerName, final Long orderId) {
        validateOrderIdByCustomerName(customerName, orderId);
        return OrderResponse.from(findOrdersByOrderId(orderId));
    }

    private void validateOrderIdByCustomerName(final String customerName, final Long orderId) {
        final Long customerId = customerDao.findIdByUserName(customerName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public OrdersResponse findOrdersByCustomerName(final String customerName) {
        final Long customerId = customerDao.findIdByUserName(customerName);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return OrdersResponse.from(orderIds.stream()
                .map(this::findOrdersByOrderId)
                .collect(Collectors.toList()));
    }

    private Orders findOrdersByOrderId(final Long orderId) {
        final List<OrderDetail> ordersDetails = new ArrayList<>();
        for (final OrderDetail orderDetail : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.findProductById(orderDetail.getProductId());
            ordersDetails.add(new OrderDetail(orderDetail, product));
        }

        return new Orders(orderId, ordersDetails);
    }
}
