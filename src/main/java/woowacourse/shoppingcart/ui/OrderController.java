package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;

@Validated
@RestController
@RequestMapping("/api/customers/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final int customerId,
                                         @RequestBody List<CartRequest> orderRequest) {
        final Long orderId = orderService.addOrders(customerId, orderRequest);
        return ResponseEntity.created(URI.create("/api/customers/orders/" + orderId)).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findOrders(@AuthenticationPrincipal final int customerId) {
        final List<OrderResponse> orderResponses = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal final int customerId,
                                                    @PathVariable Long orderId) {
        final OrderResponse orderResponse = orderService.findOrder(orderId, customerId);
        return ResponseEntity.ok(orderResponse);
    }
}