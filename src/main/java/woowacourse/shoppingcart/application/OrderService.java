package woowacourse.shoppingcart.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.order.OrderDetail;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.order.FindOrderResponse;
import woowacourse.shoppingcart.dto.order.MakeOrderResponse;
import woowacourse.shoppingcart.dto.order.OrderDetailDto;
import woowacourse.shoppingcart.dto.order.OrderSaveRequest;

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
        Long ordersId = orderDao.addOrders(customerId);

        int totalPrice = 0;
        for (Long productId : productIds) {
            Cart cart = extractCart(customerId, ordersId, productId);
            Product findProduct = productDao.findProductById(cart.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));
            totalPrice += cart.getQuantity() * findProduct.getPrice();
            orderDetails.add(new OrderDetailDto(cart.getProductId(), findProduct.getName(), cart.getQuantity(),
                    findProduct.getPrice(), findProduct.getImage()));
        }

        return new MakeOrderResponse(ordersId, orderDetails, totalPrice, LocalDateTime.now());
    }

    @Transactional
    public FindOrderResponse findOrderById(String email, Long orderId) {
        List<OrderDetail> orderDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();

        int totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            Long productId = orderDetail.getProductId();
            Product product = productDao.findProductById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));

            totalPrice += orderDetail.getQuantity() * product.getPrice();
            orderDetailDtos.add(new OrderDetailDto(product.getId(), product.getName(), orderDetail.getQuantity(),
                    product.getPrice(), product.getImage()));
        }

        return new FindOrderResponse(orderId, orderDetailDtos, totalPrice, LocalDateTime.now());
    }

    private Cart extractCart(Long customerId, Long ordersId, Long productId) {
        Cart cart = cartDao.findByCustomerIdAndProductId(customerId, productId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 해당 물건이 존재하지 않습니다"));
        ordersDetailDao.addOrdersDetail(new OrderDetail(ordersId, productId, cart.getQuantity()));
        return cart;
    }
}
