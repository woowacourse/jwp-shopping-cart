package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderRequest;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final OrdersDetailDao ordersDetailDao;

    public OrderService(OrderDao orderDao, ProductDao productDao, OrdersDetailDao ordersDetailDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.ordersDetailDao = ordersDetailDao;
    }

    public Long addOrders(int customerId, OrderRequest orderRequest) {
        final List<Long> productIds = getProductId(orderRequest);
        checkProductIds(productIds);
        // todo quantity까지 점검해야함 -> cart(product, quantity)를 만들기
        final Long orderId = orderDao.addOrders(customerId);
        addOrdersDetail(orderId, orderRequest);

        return orderId;
    }

    private void addOrdersDetail(Long orderId, OrderRequest orderRequest) {
        for (CartRequest cart : orderRequest.getCart()) {
            ordersDetailDao.addOrdersDetail(orderId, cart.getProductId(), cart.getQuantity());
        }
    }

    private List<Long> getProductId(OrderRequest orderRequest) {
        return orderRequest.getCart()
                .stream()
                .map(CartRequest::getProductId)
                .collect(Collectors.toList());
    }

    private void checkProductIds(List<Long> productIds) {
        productIds.forEach(productDao::findProductById);
    }

//
//    public Long addOrder(final List<OrderRequest> orderDetailRequests, final String email) {
//        final int customerId = customerDao.findByEmail(email).getId();
//        final Long ordersId = orderDao.addOrders((long) customerId);
//
//        for (final OrderRequest orderDetail : orderDetailRequests) {
//            final Long cartId = orderDetail.getCartId();
//            final Long productId = cartItemDao.findProductIdById(cartId);
//            final int quantity = orderDetail.getQuantity();
//
//            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
//            cartItemDao.deleteCartItem(cartId);
//        }
//
//        return ordersId;
//    }
//
//    public Orders findOrderById(final String customerName, final Long orderId) {
//        validateOrderIdByCustomerName(customerName, orderId);
//        return findOrderResponseDtoByOrderId(orderId);
//    }
//
//    private void validateOrderIdByCustomerName(final String email, final Long orderId) {
//        final int customerId = customerDao.findByEmail(email).getId();
//
//        if (!orderDao.isValidOrderId((long) customerId, orderId)) {
//            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
//        }
//    }
//
//    public List<Orders> findOrdersByCustomerName(final String email) {
//        final int customerId = customerDao.findByEmail(email).getId();
//        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId((long) customerId);
//
//        return orderIds.stream()
//                .map(orderId -> findOrderResponseDtoByOrderId(orderId))
//                .collect(Collectors.toList());
//    }
//
//    private Orders findOrderResponseDtoByOrderId(final Long orderId) {
//        final List<OrderDetail> ordersDetails = new ArrayList<>();
//        for (final OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
//            final Product product = productDao.findProductById(productQuantity.getProductId());
//            final int quantity = productQuantity.getQuantity();
//            ordersDetails.add(new OrderDetail(product, quantity));
//        }
//
//        return new Orders(orderId, ordersDetails);
//    }
}
