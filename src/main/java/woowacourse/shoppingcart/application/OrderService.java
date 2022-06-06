package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.repository.dao.CartItemDao;
import woowacourse.shoppingcart.repository.dao.OrderDao;
import woowacourse.shoppingcart.repository.dao.OrdersDetailDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;

    public OrderService(final OrderDao orderDao,
                        final OrdersDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
    }

    public Long addOrder(final List<OrderRequest> orderDetailRequests, final Long customerId) {
        final Long ordersId = orderDao.create(customerId);

        for (final OrderRequest orderDetail : orderDetailRequests) {
            final Long cartId = orderDetail.getId();
            final Long productId = cartItemDao.findProductIdById(cartId);
            final int quantity = orderDetail.getQuantity();

            ordersDetailDao.create(ordersId, productId, quantity);
            cartItemDao.deleteById(cartId);
        }

        return ordersId;
    }
}
