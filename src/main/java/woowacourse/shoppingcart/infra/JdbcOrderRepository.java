package woowacourse.shoppingcart.infra;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.infra.dao.OrderDao;
import woowacourse.shoppingcart.infra.dao.entity.CartEntity;
import woowacourse.shoppingcart.infra.dao.entity.OrderEntity;
import woowacourse.shoppingcart.infra.dao.entity.ProductEntity;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final OrderDao orderDao;
    private final CartRepository cartRepository;

    public JdbcOrderRepository(final OrderDao orderDao, final CartRepository cartRepository) {
        this.orderDao = orderDao;
        this.cartRepository = cartRepository;
    }

    @Override
    public long save(final List<Cart> orderingCarts) {
        return orderDao.save(toCartEntities(orderingCarts));
    }

    @Override
    public Optional<Order> findOrderById(final long orderId) {
        final Optional<List<OrderEntity>> optionalOrderEntities = orderDao.findOrderById(orderId);
        if (optionalOrderEntities.isEmpty()) {
            return Optional.empty();
        }

        final List<OrderEntity> orderEntities = optionalOrderEntities.get();
        final List<OrderDetail> orderDetails = orderEntities.stream()
                .map(entity -> new OrderDetail(entity.getOrderDetailId(), toCart(entity)))
                .collect(Collectors.toList());

        return Optional.of(new Order(orderEntities.get(0).getOrderId(), orderDetails));
    }

    @Override
    public List<Order> findOrdersByCustomerId(final long customerId) {
        final Optional<List<OrderEntity>> optionalOrderEntities = orderDao.findOrdersByCustomerId(customerId);
        if (optionalOrderEntities.isEmpty()) {
            return Collections.emptyList();
        }

        final List<OrderEntity> orderEntities = optionalOrderEntities.get();
        return orderEntities.stream()
                .collect(Collectors.groupingBy(OrderEntity::getOrderId, Collectors.toList()))
                .entrySet()
                .stream()
                .map(entry -> new Order(entry.getKey(), entry.getValue().stream()
                        .map(entity -> new OrderDetail(entity.getOrderDetailId(), toCart(entity)))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    private Cart toCart(final OrderEntity entity) {
        return new Cart(entity.getCustomerId(), toProduct(entity.getProductEntity()), entity.getQuantity());
    }

    private List<CartEntity> toCartEntities(final List<Cart> orderingCarts) {
        return orderingCarts.stream()
                .map(this::toCartEntity)
                .collect(Collectors.toList());
    }

    private CartEntity toCartEntity(final Cart cart) {
        return new CartEntity(cart.getId(), cart.getCustomerId(), toProductEntity(cart.getProduct()),
                cart.getQuantity());
    }

    private ProductEntity toProductEntity(final Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    private Product toProduct(final ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getPrice(),
                productEntity.getImageUrl());
    }
}
