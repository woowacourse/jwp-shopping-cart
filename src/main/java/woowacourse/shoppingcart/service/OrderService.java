package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.OrdersDao;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrderProductResponse;
import woowacourse.shoppingcart.dto.response.OrderResponse;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {
    private final OrdersDao ordersDao;
    private final ProductRepository productRepository;

    public OrderService(OrdersDao ordersDao, ProductRepository productRepository) {
        this.ordersDao = ordersDao;
        this.productRepository = productRepository;
    }

    public Long addOrder(final List<OrderRequest> orderRequests, final Long customerId) {
        List<OrderDetail> orderDetails = orderRequests.stream()
                .map(orderRequest -> new OrderDetail(productRepository.findById(orderRequest.getProductId()),
                        orderRequest.getQuantity())).collect(
                        Collectors.toList());

        Orders orders = new Orders(orderDetails, customerId);
        return ordersDao.save(orders);
    }

    public OrderResponse findOrderById(final Long customerId, final Long orderId) {
        validateOrderIdByCustomerId(customerId, orderId);
        Orders orders = ordersDao.findById(orderId);

        return convertOrderToResponse(orders);
    }

    private void validateOrderIdByCustomerId(final Long customerId, final Long orderId) {
        if (!ordersDao.isValidOrderId(customerId, orderId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<OrderResponse> findOrdersByCustomerId(final Long customerId) {
        List<Orders> orders = ordersDao.findAllByCustomerId(customerId);

        return orders.stream().map(this::convertOrderToResponse).collect(Collectors.toList());
    }

    private OrderResponse convertOrderToResponse(Orders orders) {
        List<OrderProductResponse> orderProductResponses = getOrderProductResponseByOrder(orders);
        return new OrderResponse(orderProductResponses);
    }

    private List<OrderProductResponse> getOrderProductResponseByOrder(Orders orders) {
        return orders.getOrderDetails().stream()
                .map(orderDetail -> new OrderProductResponse(new ProductResponse(orderDetail.getProduct()),
                        orderDetail.getQuantity())).collect(
                        Collectors.toList());
    }
}
