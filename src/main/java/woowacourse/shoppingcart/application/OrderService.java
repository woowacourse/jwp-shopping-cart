package woowacourse.shoppingcart.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Long ordersId = orderDao.addOrders(customerId);
        List<OrderDetailDto> orderDetails = new ArrayList<>();
        Map<Long, Product> productCache = new HashMap<>();

        for (Long productId : productIds) {
            Cart extractedCart = extractCart(customerId, ordersId, productId);
            cacheProduct(productCache, productId);
            Product foundProduct = productCache.get(productId);
            orderDetails.add(new OrderDetailDto(extractedCart, foundProduct));
        }

        int totalPrice = calculateTotalPriceFromDtos(orderDetails);
        return new MakeOrderResponse(ordersId, orderDetails, totalPrice, LocalDateTime.now());
    }

    @Transactional
    public FindOrderResponse findOrderById(Long orderId) {
        List<OrderDetail> orderDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        Map<Long, Product> productCache = new HashMap<>();

        for (OrderDetail orderDetail : orderDetails) {
            Long productId = orderDetail.getProductId();
            cacheProduct(productCache, productId);

            Product foundProduct = productCache.get(productId);
            orderDetailDtos.add(new OrderDetailDto(foundProduct, orderDetail));
        }

        int totalPrice = calculateTotalPriceFromDtos(orderDetailDtos);
        return new FindOrderResponse(orderId, orderDetailDtos, totalPrice, LocalDateTime.now());
    }

    private int calculateTotalPriceFromDtos(List<OrderDetailDto> orderDetailDtos) {
        return orderDetailDtos.stream()
                .map(dto -> dto.getPrice() * dto.getQuantity())
                .reduce(0, Integer::sum);
    }

    private void cacheProduct(Map<Long, Product> productCache, Long productId) {
        if (!productCache.containsKey(productId)) {
            Product product = productDao.findProductById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다"));
            productCache.put(productId, product);
        }
    }

    private Cart extractCart(Long customerId, Long ordersId, Long productId) {
        Cart cart = cartDao.findByCustomerIdAndProductId(customerId, productId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 해당 물건이 존재하지 않습니다"));
        ordersDetailDao.addOrdersDetail(new OrderDetail(ordersId, productId, cart.getQuantity()));
        return cart;
    }
}
