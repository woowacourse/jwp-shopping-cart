package woowacourse.shoppingcart.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.dto.request.CartItemsRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {
    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CustomerDao customerDao;
    private final CartService cartService;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CustomerDao customerDao, final CartService cartService) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.customerDao = customerDao;
        this.cartService = cartService;
    }

    public Long addOrder(CartItemsRequest cartItemsRequest, String username) {
        List<OrderDetail> orderDetails = checkOutFromCart(cartItemsRequest, username);

        final Long customerId = customerDao.getIdByUsername(username);
        Long orderId = orderDao.addOrders(customerId);

        for (OrderDetail orderDetail : orderDetails) {
            ordersDetailDao.addOrdersDetail(orderDetail, orderId);
        }

        return orderId;
    }

    private List<OrderDetail> checkOutFromCart(CartItemsRequest cartItemsRequest, String username) {
        Cart cart = cartService.findCartByUsername(username);
        List<Long> productIds = cartItemsRequest.getProductIds();
        return cart.checkOut(productIds);
    }
}
