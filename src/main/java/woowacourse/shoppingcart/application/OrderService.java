package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.OrderDetailResponse;
import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrdersDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;
import woowacourse.shoppingcart.dao.entity.OrdersDetailEntity;
import woowacourse.shoppingcart.dao.entity.OrdersEntity;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;
import woowacourse.shoppingcart.ui.dto.OrderRequest;

@Service
@Transactional(rollbackFor = Exception.class)
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

    public Long save(OrderRequest orderRequest, final Long customerId) {
        final Long ordersId = ordersDao.save(customerId);

        for (final OrderDetailRequest orderDetail : orderRequest.getOrder()) {
            final Long cartId = orderDetail.getId();
            final Long productId = getCartItemEntity(cartId).getProductId();
            final int quantity = orderDetail.getQuantity();

            ordersDetailDao.save(new OrdersDetailEntity(null, ordersId, productId, quantity));
            cartItemDao.delete(new CartItemEntity(customerId, productId));
        }

        return ordersId;
    }

    private CartItemEntity getCartItemEntity(Long cartId) {
        return cartItemDao.findById(cartId)
                .orElseThrow(InvalidCartItemException::new);
    }

    public OrderResponse findById(final Long customerId, final Long orderId) {
        validateOrderIdByCustomerName(customerId, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(final Long customerId, final Long orderId) {
        if (!ordersDao.existsOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<OrderResponse> findByCustomerId(final Long customerId) {
        final List<Long> orderIds = ordersDao.findByCustomerId(customerId)
                .stream()
                .map(OrdersEntity::getId)
                .collect(Collectors.toUnmodifiableList());

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }

    private OrderResponse findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetailResponse> products = new ArrayList<>();
        for (final OrdersDetailEntity ordersDetail : ordersDetailDao.findByOrderId(orderId)) {
            final Product product = findProductById(ordersDetail.getProductId());
            final int quantity = ordersDetail.getQuantity();
            products.add(OrderDetailResponse.from(product, quantity));
        }

        return new OrderResponse(orderId, products);
    }

    private Product findProductById(Long id) {
        return productDao.findById(id)
                .orElseThrow(InvalidProductException::new)
                .toProduct();
    }
}
