package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.entity.OrdersDetailEntity;

public interface OrdersDetailDao {
    Long addOrdersDetail(final Long ordersId, final Long productId, final int quantity);

    List<OrdersDetailEntity> findOrdersDetailsByOrderId(final Long orderId);
}
