package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.entity.OrdersEntity;

public interface OrdersDao {
    Long save(Orders orders);

    OrdersEntity findById(Long id);

    List<OrdersEntity> findAllByCustomerId(Long customerId);

    boolean isValidOrderId(Long customerId, Long orderId);
}
