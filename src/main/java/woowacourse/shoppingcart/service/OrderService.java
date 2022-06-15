package woowacourse.shoppingcart.service;

import java.util.List;
import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.ui.dto.request.OrderCreateRequest;

public interface OrderService {
    long order(long id, List<OrderCreateRequest> orderCreateRequests);

    Order findByOrderId(long orderId);

    List<Order> findOrdersByCustomerId(long customerId);
}
