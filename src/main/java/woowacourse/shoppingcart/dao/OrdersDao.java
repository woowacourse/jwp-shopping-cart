package woowacourse.shoppingcart.dao;

import java.util.List;
import java.util.Optional;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.entity.OrdersEntity;

public interface OrdersDao {
    Long save(Orders orders);

    Optional<OrdersEntity> findById(Long id);

    List<OrdersEntity> findAllByCustomerId(Long customerId);
}
