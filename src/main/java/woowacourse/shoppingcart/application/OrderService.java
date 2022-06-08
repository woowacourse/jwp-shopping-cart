package woowacourse.shoppingcart.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.order.Orders;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.order.MakeOrderResponse;
import woowacourse.shoppingcart.dto.order.OrderDetailDto;
import woowacourse.shoppingcart.dto.order.OrderSaveRequest;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderDao orderDao;
    private final OrdersDetailDao ordersDetailDao;
    private final CartDao cartDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public OrderService(OrderDao orderDao, OrdersDetailDao ordersDetailDao,
                        CartDao cartItemDao, CustomerDao customerDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.ordersDetailDao = ordersDetailDao;
        this.cartDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public MakeOrderResponse addOrder(OrderSaveRequest orderSaveRequest, String email) {
        List<Long> productIds = orderSaveRequest.getProductIds();
        Long customerId = customerDao.findIdByEmail(email);
        List<OrderDetailDto> orderDetails = new ArrayList<>();

        int totalPrice = 0;
        for (Long productId : productIds) {
            Cart cart = cartDao.findByCustomerIdAndProductId(customerId, productId)
                    .orElseThrow(() -> new IllegalArgumentException("장바구니에 해당 물건이 존재하지 않습니다"));
            Long ordersId = orderDao.addOrders(customerId);
            ordersDetailDao.addOrdersDetail(new OrderDetail(ordersId, productId, cart.getQuantity()));

            Product findProduct = productDao.findProductById(cart.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));
            totalPrice += cart.getQuantity() * findProduct.getPrice();
            orderDetails.add(new OrderDetailDto(cart.getProductId(), findProduct.getName(), cart.getQuantity(),
                    findProduct.getPrice(), findProduct.getImage()));
        }

        return new MakeOrderResponse(customerId, orderDetails, totalPrice, LocalDateTime.now());
    }

    public Orders findOrderById(String customerName, Long orderId) {
        validateOrderIdByCustomerName(customerName, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    private void validateOrderIdByCustomerName(String customerName, Long orderId) {
        Long customerId = customerDao.findIdByUserName(customerName);

        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<Orders> findOrdersByCustomerName(String customerName) {
        Long customerId = customerDao.findIdByUserName(customerName);
        List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);

        return orderIds.stream()
                .map(orderId -> findOrderResponseDtoByOrderId(orderId))
                .collect(Collectors.toList());
    }

    private Orders findOrderResponseDtoByOrderId(Long orderId) {
        List<OrderDetail> ordersDetails = new ArrayList<>();
        for (OrderDetail productQuantity : ordersDetailDao.findOrdersDetailsByOrderId(orderId)) {
            Product product = productDao.findProductById(productQuantity.getProductId()).get();
            int quantity = productQuantity.getQuantity();
//            ordersDetails.add(new OrderDetail(product, quantity));
        }

        return new Orders(orderId, ordersDetails);
    }
}
