package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.OrderDetail;

public interface OrderDetailDao {
    void save(long orderId, long productId, int quantity);

    List<OrderDetail> findAllByOrderId(Long orderId);
}
