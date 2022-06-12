package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import woowacourse.auth.domain.user.Customer;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.entity.OrderDetailEntity;
import woowacourse.shoppingcart.entity.OrderEntity;

@Repository
public class OrderRepository {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;

    public OrderRepository(OrderDao orderDao, OrderDetailDao orderDetailDao) {
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
    }

    public Long save(Customer customer, Order order) {
        OrderEntity savedOrder = orderDao.save(new OrderEntity(customer.getId()));
        Long orderId = savedOrder.getId();
        List<OrderDetailEntity> orderDetailEntities = order.getCartItems()
                .stream()
                .map(it -> new OrderDetailEntity(orderId, it.getProductId(), it.getQuantity()))
                .collect(Collectors.toList());
        orderDetailDao.save(orderDetailEntities);
        return orderId;
    }
}
