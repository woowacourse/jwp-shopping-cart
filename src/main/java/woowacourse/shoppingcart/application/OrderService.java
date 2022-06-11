package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrderDetailDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;
import woowacourse.shoppingcart.exception.CartNotFoundException;
import woowacourse.shoppingcart.exception.InvalidOrderException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;
    private final CartDao cartDao;

    public OrderService(OrderDao orderDao, OrderDetailDao orderDetailDao, CartDao cartDao) {
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.cartDao = cartDao;
    }

    public Long addOrder(List<OrderRequest> orderDetailRequests, Long memberId) {
        Long ordersId = orderDao.addOrders(memberId);

        for (OrderRequest orderDetail : orderDetailRequests) {
            Long cartId = orderDetail.getCartId();
            Cart cart = cartDao.findCartById(cartId)
                    .orElseThrow(CartNotFoundException::new);
            orderDetailDao.save(ordersId, cart.getProductId(), cart.getQuantity());
            cartDao.deleteById(cartId);
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
