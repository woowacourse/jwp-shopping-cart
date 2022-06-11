package woowacourse.shoppingcart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.OrdersRequest;
import woowacourse.shoppingcart.dto.response.OrderProductResponse;
import woowacourse.shoppingcart.dto.response.OrdersResponse;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidOrderException;
import woowacourse.shoppingcart.repository.OrdersRepository;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;

    public OrdersService(OrdersRepository ordersRepository, ProductRepository productRepository) {
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
    }

    public Long addOrder(List<OrdersRequest> ordersRequests, Long customerId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrdersRequest ordersRequest : ordersRequests) {
            Product product = productRepository.findById(ordersRequest.getProductId());
            int quantity = ordersRequest.getQuantity();
            OrderDetail orderDetail = new OrderDetail(product, quantity);
            orderDetails.add(orderDetail);
        }

        Orders orders = new Orders(orderDetails, customerId);
        return ordersRepository.save(orders);
    }

    public OrdersResponse findOrdersById(Long customerId, Long orderId) {
        Orders orders = ordersRepository.findOrdersById(orderId);
        validateOrderIdByCustomerId(orders, customerId);
        return convertOrderToResponse(orders);
    }

    private void validateOrderIdByCustomerId(Orders orders, Long customerId) {
        if (!orders.getCustomerId().equals(customerId)) {
            throw new InvalidOrderException("유저에게는 해당 order_id가 없습니다.");
        }
    }

    public List<OrdersResponse> findOrdersByCustomerId(Long customerId) {
        List<Orders> orders = ordersRepository.findAllByCustomerId(customerId);

        return orders.stream().map(this::convertOrderToResponse).collect(Collectors.toList());
    }

    private OrdersResponse convertOrderToResponse(Orders orders) {
        List<OrderProductResponse> orderProductResponses = getOrderProductResponseByOrder(orders);
        return new OrdersResponse(orderProductResponses);
    }

    private List<OrderProductResponse> getOrderProductResponseByOrder(Orders orders) {
        return orders.getOrderDetails().stream()
                .map(orderDetail -> new OrderProductResponse(new ProductResponse(orderDetail.getProduct()),
                        orderDetail.getQuantity())).collect(
                        Collectors.toList());
    }
}
