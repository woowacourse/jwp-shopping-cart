package woowacourse.order.ui;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.order.application.OrderService;
import woowacourse.order.dto.OrderAddRequest;
import woowacourse.order.dto.OrderResponse;
import woowacourse.order.dto.OrderResponses;

@RequestMapping("/api/orders")
@RestController
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(
        @AuthenticationPrincipal final String username,
        @Valid @RequestBody final List<OrderAddRequest> orderAddRequests
    ) {
        final Long orderId = orderService.addOrder(username, orderAddRequests);
        return ResponseEntity.created(URI.create("/api/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(
        @AuthenticationPrincipal final String username,
        @PathVariable final Long orderId
    ) {
        return ResponseEntity.ok(orderService.findOrderById(username, orderId));
    }

    @GetMapping
    public ResponseEntity<OrderResponses> findOrders(@AuthenticationPrincipal final String username) {
        return ResponseEntity.ok(orderService.findOrdersByCustomerName(username));
    }
}