package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.order.OrderResponse;
import woowacourse.shoppingcart.dto.order.OrderResponses;
import woowacourse.shoppingcart.dto.order.OrderSaveRequests;

@RestController
@RequestMapping("/api/customers/{customerName}/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@PathVariable final String customerName,
                                         @RequestBody final OrderSaveRequests orderSaveRequests) {
        final Long orderId = orderService.addOrder(orderSaveRequests, customerName);
        return ResponseEntity.created(
                URI.create("/api/" + customerName + "/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@PathVariable final String customerName,
                                                   @PathVariable final Long orderId) {
        final OrderResponse response = orderService.findOrderById(customerName, orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<OrderResponses> findOrders(@PathVariable final String customerName) {
        final OrderResponses response = orderService.findOrdersByCustomerName(customerName);
        return ResponseEntity.ok(response);
    }
}
