package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.OrdersDetailDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cartItem.CartItem;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderDetailResponse;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.entity.OrdersDetailEntity;
import woowacourse.shoppingcart.entity.ProductEntity;
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

    public Long addOrders(int customerId, List<CartRequest> orderRequest) {
        final List<CartItem> cartItems = getCartItems(orderRequest);

        final Long orderId = orderDao.addOrders(customerId);

        addOrdersDetail(orderId, cartItems);

        return orderId;
    }

    private List<CartItem> getCartItems(List<CartRequest> orderRequest) {
        return orderRequest
                .stream()
                .map(cartRequest -> getCartItem(cartRequest.getProductId(), cartRequest.getQuantity()))
                .collect(Collectors.toList());
    }

    private CartItem getCartItem(Long productId, int quantity) {
        final ProductEntity productEntity = productDao.findProductById(productId);
        return CartItem.of(productEntity.getId(), productEntity.getName(), productEntity.getPrice(),
                productEntity.getImageUrl(), productEntity.getDescription(), productEntity.getStock(), quantity);
    }

    private void addOrdersDetail(Long orderId, List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            ordersDetailDao.addOrdersDetail(orderId, cartItem.getProduct().getId(), cartItem.getQuantity().getValue());
        }
    }

    public List<OrderResponse> findOrdersByCustomerId(final int customerId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);
        return orderIds.stream()
                .map(ordersDetailDao::findOrdersDetailsByOrderId)
                .map(this::convertOrderResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse convertOrderResponse(List<OrdersDetailEntity> ordersDetailEntities){
        final List<OrderDetailResponse> orderDetailResponses = getOrderDetailResponses(ordersDetailEntities);

        int totalPrice = getTotalPrice(orderDetailResponses);

        return new OrderResponse(orderDetailResponses, totalPrice);
    }

    private List<OrderDetailResponse> getOrderDetailResponses(List<OrdersDetailEntity> ordersDetailEntities) {
        return ordersDetailEntities.stream()
                .map(entity -> new OrderDetailResponse(
                        new ProductResponse(entity.getProductId(), entity.getName(), entity.getPrice(),
                                entity.getImageUrl(), entity.getDescription(), entity.getStock()),
                        entity.getQuantity())
                ).collect(Collectors.toList());
    }

    private int getTotalPrice(List<OrderDetailResponse> orderDetailResponses) {
        return orderDetailResponses.stream()
                .mapToInt(cartItemResponse -> cartItemResponse.getProduct().getPrice() * cartItemResponse.getQuantity())
                .sum();
    }

    public OrderResponse findOrder(Long orderId, int customerId) {
        validateOrderId(orderId, customerId);
        final List<OrdersDetailEntity> ordersDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        return convertOrderResponse(ordersDetails);
    }

    private void validateOrderId(Long orderId, int customerId) {
        if (!orderDao.isValidOrderId(orderId, customerId)) {
            throw new InvalidOrderException();
        }
    }
}
