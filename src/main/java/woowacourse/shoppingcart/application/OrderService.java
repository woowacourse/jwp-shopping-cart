package woowacourse.shoppingcart.application;

import static woowacourse.shoppingcart.application.ProductService.convertProductEntityToResponse;

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
                .map(cartRequest -> {
                    final ProductEntity productEntity = productDao.findProductById(cartRequest.getProductId());
                    return CartItem.of(productEntity.getId(), productEntity.getName(), productEntity.getPrice(),
                            productEntity.getImageUrl(), productEntity.getDescription(),
                            productEntity.getStock(),
                            cartRequest.getQuantity());
                })
                .collect(Collectors.toList());
    }

    private void addOrdersDetail(Long orderId, List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            ordersDetailDao.addOrdersDetail(orderId, cartItem.getProduct().getId(), cartItem.getQuantity().getValue());
        }
    }

    public List<OrderResponse> findOrdersByCustomerId(final int customerId) {
        final List<Long> orderIds = orderDao.findOrderIdsByCustomerId(customerId);
        return getOrderResponses(orderIds);
    }

    private List<OrderResponse> getOrderResponses(List<Long> orderIds) {
        return orderIds.stream()
                .map(ordersDetailDao::findOrdersDetailsByOrderId)
                .map(ordersDetailEntity -> {
                    final List<OrderDetailResponse> orderDetailResponses = getCartItemResponse(ordersDetailEntity);

                    final int totalPrice = getTotalPrice(orderDetailResponses);

                    return new OrderResponse(orderDetailResponses, totalPrice);
                })
                .collect(Collectors.toList());
    }

    private List<OrderDetailResponse> getCartItemResponse(List<OrdersDetailEntity> ordersDetailEntity) {
        return ordersDetailEntity.stream()
                .map(this::getCartResponse)
                .collect(Collectors.toList());
    }

    private int getTotalPrice(List<OrderDetailResponse> orderDetailResponses) {
        return orderDetailResponses.stream()
                .mapToInt(cartItemResponse -> cartItemResponse.getProduct().getPrice() * cartItemResponse.getQuantity())
                .sum();
    }

    private OrderDetailResponse getCartResponse(OrdersDetailEntity ordersDetailEntity) {
        final ProductEntity productEntity = productDao.findProductById(ordersDetailEntity.getProductId());
        final int quantity = ordersDetailEntity.getQuantity();
        return new OrderDetailResponse(convertProductEntityToResponse(productEntity), quantity);
    }

    public OrderResponse findOrder(Long orderId, int customerId) {
        validateOrderId(orderId, customerId);
        final List<OrdersDetailEntity> ordersDetails = ordersDetailDao.findOrdersDetailsByOrderId(orderId);
        final List<OrderDetailResponse> orderDetailResponses = getCartItemResponse(ordersDetails);
        final int totalPrice = getTotalPrice(orderDetailResponses);

        return new OrderResponse(orderDetailResponses, totalPrice);
    }

    private void validateOrderId(Long orderId, int customerId) {
        if (!orderDao.isValidOrderId(orderId, customerId)) {
            throw new InvalidOrderException();
        }
    }
}
