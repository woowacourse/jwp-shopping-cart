package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.dto.ErrorResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.exception.OutOfStockException;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;

    public OrderService(OrderDao orderDao, OrdersDetailDao ordersDetailDao,
                        CartItemDao cartItemDao, CustomerDao customerDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
    }

    @Transactional
    public Long addOrder(List<Long> cartItemIds, String email) {
        final Customer customer = customerDao.findByEmail(email);

        final Long ordersId = orderDao.addOrders(customer.getId());
        final List<CartItem> cartItems = cartItemDao.findByIds(cartItemIds);

        for (CartItem cartItem : cartItems) {
            if (cartItem.getQuantity() > cartItem.getProduct().getStockQuantity()) {
                throw new OutOfStockException("재고가 부족합니다.", ErrorResponse.OUT_OF_STOCK);
            }
            ordersDetailDao.addOrdersDetail(ordersId, cartItem.getProduct().getId(), cartItem.getQuantity());
            cartItemDao.deleteCartItem(cartItem.getId());
        }

        return ordersId;
    }

    public List<OrderResponse> findOrdersByEmail(String email) {
        final Customer customer = customerDao.findByEmail(email);
        List<Long> ordersIds = orderDao.findOrderIdsByCustomerId(customer.getId());
        return ordersIds.stream()
                .map(this::makeOrderResponseByOrderId)
                .collect(Collectors.toUnmodifiableList());
    }

    private OrderResponse makeOrderResponseByOrderId(Long orderId) {
        List<OrderDetail> orderDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        final List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(OrderDetailResponse::of)
                .collect(Collectors.toUnmodifiableList());
        return new OrderResponse(orderId, orderDetailResponses);
    }
}
