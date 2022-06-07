package woowacourse.shoppingcart.domain;

import java.util.List;

public interface OrderRepository {

    List<NewOrders> findOrders(long customerId);

    long add(long customerId, NewOrders newOrders);
}
