package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
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
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.OrdersRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;

@Validated
@RestController
@RequestMapping("/customers/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final long customerId,
                                         @RequestBody final OrdersRequest orderDetails) {
        final Long orderId = orderService.addOrder(customerId, orderDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal long customerId,
                                                   @PathVariable final Long orderId) {
        final OrderResponse orderResponse = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<OrdersResponse> findOrders(@AuthenticationPrincipal long customerId) {
        final OrdersResponse ordersResponse = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(ordersResponse);
    }
}
