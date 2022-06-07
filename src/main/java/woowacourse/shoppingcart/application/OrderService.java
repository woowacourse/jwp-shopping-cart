package woowacourse.shoppingcart.application;

import static woowacourse.shoppingcart.application.ProductService.convertResponseToProduct;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.entity.OrdersDetailEntity;
import woowacourse.shoppingcart.exception.InvalidOrderException;

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

    public List<OrderResponse> findOrdersByCustomerId(final int customerId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);
        return getOrderResponses(orderIds);
    }

    private List<OrderResponse> getOrderResponses(List<Long> orderIds) {
        return orderIds.stream()
                .map(ordersDetailDao::findOrdersDetailsByOrderId)
                .map(ordersDetailEntity -> {
                    final List<CartItemResponse> cartItemResponses = getCartItemResponse(ordersDetailEntity);

                    final int totalPrice = getTotalPrice(cartItemResponses);

                    return new OrderResponse(cartItemResponses, totalPrice);
                })
                .collect(Collectors.toList());
    }

    private List<CartItemResponse> getCartItemResponse(List<OrdersDetailEntity> ordersDetailEntity) {
        return ordersDetailEntity.stream()
                .map(this::getCartResponse)
                .collect(Collectors.toList());
    }

    private int getTotalPrice(List<CartItemResponse> cartItemResponses) {
        return cartItemResponses.stream()
                .mapToInt(cartItemResponse -> cartItemResponse.getProduct().getPrice()
                        * cartItemResponse.getQuantity())
                .sum();
    }


    private CartItemResponse getCartResponse(OrdersDetailEntity ordersDetailEntity) {
        final Product product = productDao.findProductById(ordersDetailEntity.getProduct_id());
        final int quantity = ordersDetailEntity.getQuantity();
        return new CartItemResponse(convertResponseToProduct(product), quantity);
    }

    public OrderResponse findOrder(Long orderId, int customerId) {
        validateOrderId(orderId, customerId);
        final List<OrdersDetailEntity> ordersDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        final List<CartItemResponse> cartItemResponses = getCartItemResponse(ordersDetails);
        final int totalPrice = getTotalPrice(cartItemResponses);

        return new OrderResponse(cartItemResponses, totalPrice);
    }

    private void validateOrderId(Long orderId, int customerId) {
        if (!orderDao.isValidOrderId(orderId, customerId)) {
            throw new InvalidOrderException();
        }
    }
}
