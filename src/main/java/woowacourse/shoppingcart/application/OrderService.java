package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final CartItemDao cartItemDao;

    public OrderService(OrderDao orderDao, OrderDetailDao orderDetailDao, CartItemDao cartItemDao) {
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.cartItemDao = cartItemDao;
    }

    public Long addOrder(List<OrderRequest> orderDetailRequests, Long memberId) {
        Long ordersId = orderDao.addOrders(memberId);

        for (OrderRequest orderDetail : orderDetailRequests) {
            Long cartId = orderDetail.getCartId();
            Long productId = cartItemDao.findProductIdById(cartId)
                    .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));
            int quantity = orderDetail.getQuantity();

            orderDetailDao.save(ordersId, productId, quantity);
            cartItemDao.deleteById(cartId);
        }
        return ordersId;
    }

    public OrdersResponse findOrder(Long memberId, Long orderId) {
        validateOrderIdByMemberId(memberId, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByMemberId(Long memberId, Long orderId) {
        if (!orderDao.isExistOrderId(memberId, orderId)) {
            throw new InvalidOrderException();
        }
    }

    public List<OrdersResponse> findOrders(Long memberId) {
        List<Long> orderIds = orderDao.findOrderIdsByMemberId(memberId);

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }

    private OrdersResponse findOrderResponseDtoByOrderId(Long orderId) {
        List<OrderDetailResponse> orderDetails = orderDetailDao.findOrdersDetailsByOrderId(orderId);
        return new OrdersResponse(orderId, orderDetails);
    }
}
