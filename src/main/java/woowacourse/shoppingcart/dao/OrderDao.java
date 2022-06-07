package woowacourse.shoppingcart.dao;

import java.util.List;

public interface OrderDao {
    Long addOrders(final int customerId);

    List<Long> findOrderIdsByCustomerId(final int customerId);
}
