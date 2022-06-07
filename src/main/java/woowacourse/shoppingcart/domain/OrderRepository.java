package woowacourse.shoppingcart.domain;

import java.util.List;

public interface OrderRepository {

    List<Orders> findOrders(long customerId);

    long add(long customerId, Orders orders);
}
