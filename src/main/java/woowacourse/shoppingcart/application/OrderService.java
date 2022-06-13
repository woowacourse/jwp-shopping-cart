package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class OrderService {
    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartDao cartDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartDao cartDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartDao = cartDao;
    }

    public Long addOrder(final List<Long> productIds, final String customerUsername) {
        validateCustomerCart(customerUsername, productIds);
        final Long orderId = orderDao.addOrders(customerUsername);
        for (Long productId : productIds) {
            final CartItem cartItem = cartDao.findCartItemByProductId(productId, customerUsername)
                    .orElseThrow(InvalidCartItemException::new);
            ordersDetailDao.addOrdersDetail(orderId, productId, cartItem.getQuantity());
        }
        return orderId;
    }

    private void validateCustomerCart(final String customerUsername, final List<Long> productIdsToOrder) {
        final List<Long> productIds = cartDao.findProductIdsByCustomerUsername(customerUsername);
        if (!productIds.containsAll(productIdsToOrder)) {
            throw new NotInCustomerCartItemException();
        }
    }
}
