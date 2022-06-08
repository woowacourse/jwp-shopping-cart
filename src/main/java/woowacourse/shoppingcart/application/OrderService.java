package woowacourse.shoppingcart.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dto.request.CartItemsRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao, final CustomerDao customerDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    public Long addOrder(CartItemsRequest cartItemsRequest, String username) {
        final Long customerId = customerDao.getIdByUsername(username);
        Long orderId = orderDao.addOrders(customerId);
        List<Long> productIds = cartItemsRequest.getProductIds();
        for (Long productId : productIds) {
            int quantity = cartItemDao.findQuantity(productId, customerId);
            ordersDetailDao.addOrdersDetail(orderId, productId, quantity);
        }
        return orderId;
    }
}
