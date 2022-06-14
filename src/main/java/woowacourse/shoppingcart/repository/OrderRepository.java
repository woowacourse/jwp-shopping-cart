package woowacourse.shoppingcart.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.application.dto.OrderDto;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Repository
public class OrderRepository {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderRepository(OrderDao orderDao, OrdersDetailDao ordersDetailDao,
                           CartItemDao cartItemDao, CustomerDao customerDao,
                           ProductDao productDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long addOrder(List<OrderDto> orderDetails, final Email email) {
        final Long customerId = customerDao.findIdByUserEmail(email);
        final Long ordersId = orderDao.addOrders(customerId);

        for (final OrderDto orderDetail : orderDetails) {
            final Long cartId = orderDetail.getCartId();
            final Long productId = cartItemDao.findProductIdById(cartId);
            final int quantity = orderDetail.getQuantity();

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItem(cartId);
        }
        return ordersId;
    }

    public List<OrderDetail> findOrderDetails(final Long orderId) {
        final List<OrderDetail> ordersDetails = new ArrayList<>();
        for (final OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.findProductById(productQuantity.getProductId());
            final int quantity = productQuantity.getQuantity();
            ordersDetails.add(new OrderDetail(product, quantity));
        }
        return ordersDetails;
    }

    public void validateOrderIdByCustomerEmail(final Email email, final Long orderId) {
        final Long customerId = customerDao.findIdByUserEmail(email);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<Long> findOrderIdsByCustomerEmail(final Email email) {
        final Long customerId = customerDao.findIdByUserEmail(email);
        return orderDao.findOrderIdsByCustomerId(customerId);
    }


}
