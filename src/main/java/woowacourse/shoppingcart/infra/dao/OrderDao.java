package woowacourse.shoppingcart.infra.dao;

import java.util.List;
import java.util.Optional;
import woowacourse.shoppingcart.infra.dao.entity.CartEntity;
import woowacourse.shoppingcart.infra.dao.entity.OrderEntity;

public interface OrderDao {
    long save(List<CartEntity> cartEntities);

    Optional<List<OrderEntity>> findOrderById(long orderId);

    Optional<List<OrderEntity>> findOrdersByCustomerId(long customerId);

    List<Long> findOrderIdsByCustomerId(Long customerId);

    boolean isValidOrderId(Long customerId, Long orderId);
}
