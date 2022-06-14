package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public OrderService(OrderDao orderDao, OrdersDetailDao ordersDetailDao, CartItemDao cartItemDao,
                        ProductDao productDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public Long addOrder(Long customerId, OrderRequest orderRequest) {
        Long orderId = orderDao.save(customerId);

        Product product = productDao.findProductById(orderRequest.getProductId());
        saveOrderDetails(orderId, orderRequest.getQuantity(), product);
        cartItemDao.deleteByCustomerIdAndProductId(customerId, product.getId());

        return orderId;
    }

    private void saveOrderDetails(Long orderId, int quantity, Product product) {
        OrderDetail orderDetail = OrderDetail.of(product, quantity);
        ordersDetailDao.save(orderId, orderDetail);
    }

    public OrderResponse findOrderById(Long customerId, Long orderId) {
        checkExistByCustomerIdAndOrderId(customerId, orderId);
        Product product = productDao.findProductById(ordersDetailDao.findProductIdByOrderId(orderId));

        return OrderResponse.from(orderId, product, ordersDetailDao.findQuantityByOrderId(orderId));
    }

    private void checkExistByCustomerIdAndOrderId(Long customerId, Long orderId) {
        if (!orderDao.existByCustomerIdAndOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<OrderResponse> findOrdersByCustomerId(Long customerId) {
        List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(orderId -> findOrderById(customerId, orderId))
                .collect(Collectors.toList());
    }
}
