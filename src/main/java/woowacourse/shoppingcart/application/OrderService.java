package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.OrderDetailResponse;
import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;
import woowacourse.shoppingcart.ui.dto.OrderRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;

    public OrderService(OrderDao orderDao, OrdersDetailDao ordersDetailDao,
                        CartItemDao cartItemDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
    }

    public Long save(OrderRequest orderRequest, final Long customerId) {
        final Long ordersId = orderDao.addOrders(customerId);

        for (final OrderDetailRequest orderDetail : orderRequest.getOrder()) {
            final Long cartId = orderDetail.getCartId();
            final Long productId = getCartItemEntity(cartId).getProductId();
            final int quantity = orderDetail.getQuantity();

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
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
        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<OrderResponse> findByCustomerId(final Long customerId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }

    private OrderResponse findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetailResponse> products = new ArrayList<>();
        for (final OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productQuantity.getProduct();
            final int quantity = productQuantity.getQuantity();
            products.add(OrderDetailResponse.from(product, quantity));
        }

        return new OrderResponse(orderId, products);
    }
}
