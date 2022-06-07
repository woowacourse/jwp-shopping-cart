package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderedProductDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrderedProductDao orderedProductDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderService(final OrderDao orderDao, final OrderedProductDao orderedProductDao,
                        final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.orderedProductDao = orderedProductDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long addOrder(final List<Long> cartIds, final String customerName) {
        final Long customerId = customerDao.getIdByEmail(customerName);
        final Long ordersId = orderDao.addOrders(customerId);

        for (Long cartId : cartIds) {
            Cart cart = cartItemDao.getById(cartId);
            orderedProductDao.save(ordersId, cart);
        }
        return ordersId;
    }

    public Orders findOrderById(final String customerName, final Long orderId) {
        validateOrderIdByCustomerName(customerName, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(final String customerName, final Long orderId) {
        final Long customerId = customerDao.getIdByEmail(customerName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<Orders> findOrdersByCustomerName(final String customerName) {
        final Long customerId = customerDao.getIdByEmail(customerName);
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(orderId -> findOrderResponseDtoByOrderId(orderId))
                .collect(Collectors.toList());
    }

    private Orders findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetail> ordersDetails = new ArrayList<>();
        for (final OrderDetail productQuantity : orderedProductDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.findProductById(productQuantity.getProductId());
            final int quantity = productQuantity.getQuantity();
            ordersDetails.add(new OrderDetail(product, quantity));
        }

        return new Orders(orderId, ordersDetails);
    }
}
