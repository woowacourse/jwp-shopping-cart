package woowacourse.shoppingcart.infra;

import java.util.List;
import java.util.Optional;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Order;

public interface OrderRepository {
    long save(List<Cart> orderingCarts);

    Optional<Order> findOrderById(long orderId);

    List<Order> findOrdersByCustomerId(long customerId);
}
