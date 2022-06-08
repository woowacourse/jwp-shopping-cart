package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.OrderDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Order;
import woowacourse.shoppingcart.domain.OrderProduct;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrderProductResponse;
import woowacourse.shoppingcart.dto.response.OrderResponse;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderDao orderDao;
    private final ProductDao productDao;

    public OrderService(final OrderDao orderDao, final ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }

    public Long addOrder(final List<OrderRequest> orderRequests, final Long customerId) {
        List<OrderProduct> orderProducts = orderRequests.stream()
                .map(orderRequest -> new OrderProduct(productDao.findProductById(orderRequest.getProductId()),
                        orderRequest.getQuantity())).collect(
                        Collectors.toList());

        Order order = new Order(orderProducts, customerId);
        return orderDao.addOrder(order);
    }

    public OrderResponse findOrderById(final Long customerId, final Long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        Order order = orderDao.findById(orderId);

        return convertOrderToResponse(order);
    }

    private void validateOrderIdByCustomerId(final Long customerId, final Long orderId) {
        if (!orderDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<OrderResponse> findOrdersByCustomerId(final Long customerId) {
        List<Order> orders = orderDao.findAllByCustomerId(customerId);

        return orders.stream().map(this::convertOrderToResponse).collect(Collectors.toList());
    }

    private OrderResponse convertOrderToResponse(Order order) {
        List<OrderProductResponse> orderProductResponses = getOrderProductResponseByOrder(order);
        return new OrderResponse(orderProductResponses);
    }

    private List<OrderProductResponse> getOrderProductResponseByOrder(Order order) {
        return order.getOrderProducts().stream()
                .map(orderProduct -> new OrderProductResponse(new ProductResponse(orderProduct.getProduct()),
                        orderProduct.getQuantity())).collect(
                        Collectors.toList());
    }
}
