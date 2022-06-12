package woowacourse.shoppingcart.application;

import java.util.ArrayList;
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
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public OrderService(OrderDao orderDao, OrdersDetailDao ordersDetailDao,
                        CartItemDao cartItemDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public Long addOrder(final List<OrderRequest> orderDetailRequests, final Long memberId) {
        final Long ordersId = orderDao.addOrders(memberId);

        for (final OrderRequest orderDetail : orderDetailRequests) {
            final Long cartId = orderDetail.getCartId();
            final Long productId = cartItemDao.findProductIdById(cartId);
            final int quantity = cartItemDao.findProductQuantityIdById(cartId);

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItem(cartId);
        }

        return ordersId;
    }

    public OrdersResponse findOrderById(final Long memberId, final Long orderId) {
        validateOrderIdByMemberId(memberId, orderId);
        return findOrdersByOrderId(orderId);
    }

    private void validateOrderIdByMemberId(final Long memberId, final Long orderId) {
        if (!orderDao.isValidOrderId(memberId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<OrdersResponse> findOrdersByMemberId(final Long memberId) {
        final List<Long> orderIds = orderDao.findOrderIdsByMemberId(memberId);

        return orderIds.stream()
                .map(this::findOrdersByOrderId)
                .collect(Collectors.toUnmodifiableList());
    }

    private OrdersResponse findOrdersByOrderId(final Long orderId) {
        final List<OrderDetail> ordersDetails = new ArrayList<>();
        for (final OrderDetail orderDetail : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            final Product product = productDao.findProductById(orderDetail.getProductId());
            final int quantity = orderDetail.getQuantity();
            ordersDetails.add(new OrderDetail(product, quantity));
        }

        List<OrderDetailResponse> orderDetailResponses = ordersDetails.stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toUnmodifiableList());
        return new OrdersResponse(orderId, orderDetailResponses);
    }
}
