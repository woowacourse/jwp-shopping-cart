package woowacourse.shoppingcart.infra.dao;

import java.util.List;
import java.util.Optional;
import woowacourse.shoppingcart.infra.dao.entity.CartEntity;
import woowacourse.shoppingcart.infra.dao.entity.OrderEntity;

public interface OrderDao {
    long save(List<CartEntity> cartEntities);

    Optional<List<OrderEntity>> findOrderById(long orderId);

    Optional<List<OrderEntity>> findAll();

    List<Long> findOrderIdsByCustomerId(final Long customerId);

    boolean isValidOrderId(final Long customerId, final Long orderId);
}
