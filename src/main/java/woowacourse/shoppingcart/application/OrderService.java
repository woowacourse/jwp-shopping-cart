package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderService(OrderDao orderDao, OrdersDetailDao ordersDetailDao,
            CartItemDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long addOrder(List<OrderRequest> orderDetailRequests, LoginCustomer loginCustomer) {
        Long customerId = customerDao.findIdByUsername(loginCustomer.getUsername());
        Long ordersId = orderDao.addOrders(customerId);

        for (OrderRequest orderDetail : orderDetailRequests) {
            Long cartId = orderDetail.getCartId();
            Long productId = cartItemDao.findProductIdById(cartId)
                    .orElseThrow(InvalidCartItemException::new);
            int quantity = orderDetail.getQuantity();

            ordersDetailDao.addOrdersDetail(ordersId, productId, quantity);
            cartItemDao.deleteCartItem(cartId);
        }

        return ordersId;
    }

    public Orders findOrderById(LoginCustomer loginCustomer, Long orderId) {
        validateOrderIdByCustomerName(loginCustomer.getUsername(), orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(String customerName, Long orderId) {
        Long customerId = customerDao.findIdByUsername(customerName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<Orders> findOrdersByCustomerName(LoginCustomer loginCustomer) {
        Long customerId = customerDao.findIdByUsername(loginCustomer.getUsername());
        List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
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
