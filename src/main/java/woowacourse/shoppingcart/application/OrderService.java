package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrdersDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;
import woowacourse.shoppingcart.dao.entity.OrdersDetailEntity;
import woowacourse.shoppingcart.dao.entity.OrdersEntity;
import woowacourse.shoppingcart.dao.entity.ProductEntity;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.order.Orders;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;
import woowacourse.shoppingcart.ui.dto.OrderRequest;

@Service
@Transactional
public class OrderService {

    private final OrdersDao ordersDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public OrderService(OrdersDao ordersDao, OrdersDetailDao ordersDetailDao,
                        CartItemDao cartItemDao, ProductDao productDao) {
        this.ordersDao = ordersDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public Long save(OrderRequest orderRequest, Long customerId) {
        final Long ordersId = ordersDao.save(customerId);

        orderRequest.getOrder()
                .forEach(orderDetailRequest -> order(ordersId, customerId, orderDetailRequest));

        return ordersId;
    }

    private void order(Long ordersId, Long customerId, OrderDetailRequest orderDetailRequest) {
        Long productId = orderDetailRequest.getId();
        int quantity = orderDetailRequest.getQuantity();
        OrderDetail orderDetail = new OrderDetail(findProductById(productId), quantity);

        ordersDetailDao.save(OrdersDetailEntity.from(ordersId, orderDetail));
        cartItemDao.delete(new CartItemEntity(customerId, productId));
    }

    private Product findProductById(Long id) {
        return productDao.findById(id)
                .orElseThrow(InvalidProductException::new)
                .toProduct();
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long customerId, Long orderId) {
        validateOrderId(customerId, orderId);
        return findById(orderId);
    }

    private void validateOrderId(Long customerId, Long orderId) {
        if (!ordersDao.existsOrderId(customerId, orderId)) {
            throw new InvalidOrderException(
                    String.format("사용자가 해당 주문을 한 적이 없습니다. 입력값: 사용자 - %d, 주문 번호 - %d", customerId, orderId)
            );
        }
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findByCustomerId(Long customerId) {
        List<Long> orderIds = ordersDao.findByCustomerId(customerId)
                .stream()
                .map(OrdersEntity::getId)
                .collect(Collectors.toUnmodifiableList());

        return orderIds.stream()
                .map(this::findById)
                .collect(Collectors.toList());
    }

    private OrderResponse findById(Long orderId) {
        List<OrdersDetailEntity> ordersDetailEntities = ordersDetailDao.findByOrderId(orderId);

        List<Product> products = findProducts(ordersDetailEntities);
        Map<Long, Product> productMap = initProductMap(products);

        List<OrderDetail> orderDetails = generateOrderDetails(productMap, ordersDetailEntities);
        Orders orders = new Orders(orderId, orderDetails);

        return OrderResponse.from(orders);
    }

    private List<Product> findProducts(List<OrdersDetailEntity> ordersDetailEntities) {
        List<Long> productIds = ordersDetailEntities.stream()
                .map(OrdersDetailEntity::getProductId)
                .collect(Collectors.toUnmodifiableList());

        return productDao.findByIds(productIds)
                .stream()
                .map(ProductEntity::toProduct)
                .collect(Collectors.toUnmodifiableList());
    }

    private Map<Long, Product> initProductMap(List<Product> products) {
        return products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
    }

    private List<OrderDetail> generateOrderDetails(Map<Long, Product> productMap,
                                                   List<OrdersDetailEntity> ordersDetailEntities) {
        return ordersDetailEntities.stream()
                .map(orderDetail -> orderDetail.toOrderDetail(productMap))
                .collect(Collectors.toUnmodifiableList());
    }
}
