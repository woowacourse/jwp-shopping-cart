package woowacourse.shoppingcart.dao;

import java.util.List;
import woowacourse.shoppingcart.domain.Order;

public interface OrderDao {
    Long save(Order order);

    Order findById(Long id);

    List<Order> findAllByCustomerId(Long customerId);

    boolean isValidOrderId(Long customerId, Long orderId);
}
