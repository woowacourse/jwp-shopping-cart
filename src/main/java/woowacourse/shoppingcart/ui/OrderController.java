package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.order.OrderResponse;
import woowacourse.shoppingcart.dto.order.OrderSaveRequest;
import woowacourse.shoppingcart.dto.order.OrdersResponse;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final String customerName,
                                         @RequestBody final List<OrderSaveRequest> orderSaveRequests) {
        final Long orderId = orderService.addOrder(orderSaveRequests, customerName);
        return ResponseEntity.created(
                URI.create("/api/" + customerName + "/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal final String customerName,
                                                   @PathVariable final Long orderId) {
        final OrderResponse response = orderService.findOrderById(customerName, orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<OrdersResponse> findOrders(@AuthenticationPrincipal final String customerName) {
        final OrdersResponse response = orderService.findOrdersByCustomerName(customerName);
        return ResponseEntity.ok(response);
    }
}
