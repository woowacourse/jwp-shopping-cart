package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.order.OrderRequest;
import woowacourse.shoppingcart.dto.order.OrdersResponse;

@Validated
@RestController
@RequestMapping("/api/customer/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final Long customerId,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, customerId);
        return ResponseEntity.created(
                URI.create("/api/customer/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal final Long customerId,
                                                    @PathVariable final Long orderId) {
        final Orders order = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(OrdersResponse.from(order));
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@AuthenticationPrincipal final Long customerId) {
        final List<Orders> orders = orderService.findOrdersByCustomerId(customerId);
        final List<OrdersResponse> ordersResponses = orders.stream()
                .map(OrdersResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ordersResponses);
    }
}
