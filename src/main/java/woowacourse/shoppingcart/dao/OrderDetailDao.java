package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.entity.OrderDetailEntity;

public interface OrderDetailDao {
    Long save(OrderDetail orderDetail, long orderId);

    List<OrderDetailEntity> findAllByOrderId(Long orderId);
}
