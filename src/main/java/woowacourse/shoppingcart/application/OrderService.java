package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.OrderDto;
import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.dto.response.OrdersResponse;
import woowacourse.shoppingcart.repository.OrderRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Long addOrder(final List<OrderDto> orderDetails, final String customerName) {
        return orderRepository.addOrder(orderDetails, customerName);
    }

    public OrdersResponse findOrderById(final String customerName, final Long orderId) {
        orderRepository.validateOrderIdByCustomerName(customerName, orderId);
        return findOrderResponseDtoByOrderId(orderId);
    }

    public List<OrdersResponse> findOrdersByCustomerName(final String customerName) {
        final List<Long> orderIds = orderRepository.findOrderIdsByCustomerName(customerName);
        return orderIds.stream()
                .map(this::findOrderResponseDtoByOrderId)
                .collect(Collectors.toList());
    }

    private OrdersResponse findOrderResponseDtoByOrderId(final Long orderId) {
        final List<OrderDetail> orderDetails = orderRepository.findOrderDetails(orderId);
        return OrdersResponse.of(orderId, orderDetails);
    }
}
