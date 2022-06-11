package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.*;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderService(final OrderDao orderDao, final OrdersDetailDao ordersDetailDao,
                        final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public Long addOrder(List<OrderRequest> orderDetailRequests, String customerName) {
        Long customerId = customerDao.findByUsername(customerName).getId();
        Long ordersId = orderDao.addOrders(customerId);

        for (OrderRequest orderDetail : orderDetailRequests) {
            Long cartId = orderDetail.getCartId();
            Long productId = cartItemDao.findCartIdById(orderDetail.getCartId())
                    .getProduct()
                    .getId();
            int quantity = orderDetail.getQuantity();

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItemById(cartId);
        }
        return ordersId;
    }

    @Transactional(readOnly = true)
    public OrdersResponse findOrderById(String customerName, Long orderId) {
        validateOrderIdByCustomerName(customerName, orderId);
        return OrdersResponse.from(findOrderResponseDtoByOrderId(orderId));
    }

    private void validateOrderIdByCustomerName(String customerName, Long orderId) {
        final Long customerId = customerDao.findByUsername(customerName).getId();

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<OrdersResponse> findOrdersByCustomerName(String customerName) {
        Long customerId = customerDao.findByUsername(customerName).getId();
        List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(id -> OrdersResponse.from(findOrderResponseDtoByOrderId(id)))
                .collect(Collectors.toList());
    }

    private Orders findOrderResponseDtoByOrderId(Long orderId) {
        List<OrderDetail> ordersDetails = new ArrayList<>();
        for (OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            Product product = productDao.findProductById(productQuantity.getProductId());
            int quantity = productQuantity.getQuantity();
            ordersDetails.add(new OrderDetail(product, quantity));
        }
        return new Orders(orderId, ordersDetails);
    }
}
