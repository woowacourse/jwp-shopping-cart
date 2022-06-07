package woowacourse.shoppingcart.repository;

import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.repository.dao.OrderDao;
import woowacourse.shoppingcart.repository.dao.OrdersDetailDao;

@Repository
public class OrderRepository {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;

    public OrderRepository(final OrderDao orderDao,
                           OrdersDetailDao ordersDetailDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
    }

    public Long create(final Long customerId, final Long productId, final int quantity) {
        Long orderId = orderDao.create(customerId);
        ordersDetailDao.create(orderId, productId, quantity);
        return orderId;
    }
}
