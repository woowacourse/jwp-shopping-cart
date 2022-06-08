package woowacourse.shoppingcart.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.order.OrderRepository;
import woowacourse.shoppingcart.domain.order.Orders;
import woowacourse.shoppingcart.entity.OrderDetailEntity;

@Repository
public class OrderRepositoryUsingDao implements OrderRepository {

    private final ProductDao productDao;
    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;

    public OrderRepositoryUsingDao(ProductDao productDao, OrderDao orderDao,
        OrdersDetailDao ordersDetailDao) {
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
    }

    @Override
    public List<Orders> findOrders(long customerId) {
        List<Long> ordersIds = orderDao.findOrderIdsByCustomerId(customerId);
        List<Orders> orders = new ArrayList<>();
        for (Long ordersId : ordersIds) {
            orders.add(new Orders(ordersId, findOrderDetails(ordersId)));
        }
        return orders;
    }

    private List<OrderDetail> findOrderDetails(Long ordersId) {
        List<OrderDetailEntity> orderDetailEntities = ordersDetailDao.findOrderDetailsByOrderId(ordersId);
        return orderDetailEntities.stream()
            .map(orderDetailEntity -> new OrderDetail(
                productDao.findProductById(orderDetailEntity.getProductId()),
                new Quantity(orderDetailEntity.getQuantity())))
            .collect(Collectors.toList());
    }

    @Override
    public long add(long customerId, Orders orders) {
        Long orderId = orderDao.addOrders(customerId);
        List<OrderDetail> orderDetails = orders.getOrderDetails();

        for (OrderDetail orderDetail : orderDetails) {
            ordersDetailDao.add(orderId, orderDetail);
        }
        return orderId;
    }
}
