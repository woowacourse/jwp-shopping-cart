package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
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
import woowacourse.shoppingcart.domain.OrderResponse;
import woowacourse.shoppingcart.dto.request.OrderRequest;

@Validated
@RestController
@RequestMapping("/customers/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal Long customerId,
                                   @RequestBody @Valid List<OrderRequest> orderDetails) {
        Long orderId = orderService.addOrder(orderDetails, customerId);
        return ResponseEntity.created(
                URI.create("/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal Long customerId,
                                            @PathVariable Long orderId) {
        OrderResponse order = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findOrders(@AuthenticationPrincipal Long customerId) {
        List<OrderResponse> orders = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }
}
