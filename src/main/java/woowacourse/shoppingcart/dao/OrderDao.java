package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.Orders;

public interface OrderDao {
    Long save(Orders orders);

    Orders findById(Long id);

    List<Orders> findAllByCustomerId(Long customerId);

    boolean isValidOrderId(Long customerId, Long orderId);
}
