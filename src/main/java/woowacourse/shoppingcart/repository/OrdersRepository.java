package woowacourse.shoppingcart.repository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.dao.OrdersDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.OrderDetailEntity;
import woowacourse.shoppingcart.entity.OrdersEntity;

@Component
public class OrdersRepository {
    private final OrdersDao ordersDao;
    private final OrderDetailDao orderDetailDao;
    private final ProductRepository productRepository;

    public OrdersRepository(OrdersDao ordersDao, OrderDetailDao orderDetailDao, ProductRepository productRepository) {
        this.ordersDao = ordersDao;
        this.orderDetailDao = orderDetailDao;
        this.productRepository = productRepository;
    }

    public long save(Orders orders) {
        Long ordersId = ordersDao.save(orders);
        saveOrderDetails(orders, ordersId);

        return ordersId;
    }

    private void saveOrderDetails(Orders orders, long id) {
        for (OrderDetail orderDetail : orders.getOrderDetails()) {
            orderDetailDao.save(orderDetail, id);
        }
    }

    public Orders findOrdersById(long id) {
        OrdersEntity ordersEntity = ordersDao.findById(id);
        return convertOrdersEntityToDomain(ordersEntity);
    }

    public List<Orders> findAllByCustomerId(long customerId) {
        List<OrdersEntity> ordersEntities = ordersDao.findAllByCustomerId(customerId);
        return ordersEntities.stream()
                .map(this::convertOrdersEntityToDomain)
                .collect(Collectors.toList());
    }

    private Orders convertOrdersEntityToDomain(OrdersEntity ordersEntity) {
        return new Orders(
                generateOrderDetails(ordersEntity.getId()),
                ordersEntity.getCustomerId()
        );
    }

    private List<OrderDetail> generateOrderDetails(long id) {
        List<OrderDetailEntity> orderDetailEntities = orderDetailDao.findAllByOrderId(id);
        return orderDetailEntities.stream()
                .map(this::convertOrderDetailEntityToDomain)
                .collect(Collectors.toList());
    }

    private OrderDetail convertOrderDetailEntityToDomain(OrderDetailEntity orderDetailEntity) {
        Product product = productRepository.findById(orderDetailEntity.getProductId());

        return new OrderDetail(
                orderDetailEntity.getId(),
                product,
                orderDetailEntity.getQuantity()
        );
    }
}
