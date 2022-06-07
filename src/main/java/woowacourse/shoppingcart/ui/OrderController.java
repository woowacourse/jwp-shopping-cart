package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.application.OrderService;

import java.net.URI;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.OrderResponses;

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
                                         @RequestBody OrderRequest orderRequest) {
        final Long orderId = orderService.addOrders(customerId, orderRequest);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(orderId)
                .toUri();

        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<OrderResponses> findOrders(@AuthenticationPrincipal final int customerId) {
        final OrderResponses orderResponses = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal final int customerId,
                                                    @PathVariable Long orderId) {
        final OrderResponse orderResponse = orderService.findOrder(orderId, customerId);
        return ResponseEntity.ok(orderResponse);
    }
}