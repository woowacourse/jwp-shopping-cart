package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartDao cartDao;
    private final CustomerDao customerDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartDao cartDao, final CustomerDao customerDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartDao = cartDao;
        this.customerDao = customerDao;
    }

    public Long addOrder(final List<Long> productIds, final String customerUsername) {
        final Long customerId = customerDao.findIdByUsername(customerUsername);
        final Long orderId = orderDao.addOrders(customerId);
        for (Long productId : productIds) {
            final CartItem cartItem = cartDao.findCartItemByProductId(productId, customerId)
                    .orElseThrow(InvalidCartItemException::new);
            ordersDetailDao.addOrdersDetail(orderId, productId, cartItem.getQuantity());
        }
        return orderId;
    }
}
