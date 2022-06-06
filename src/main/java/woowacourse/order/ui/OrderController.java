package woowacourse.order.ui;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.order.application.OrderService;
import woowacourse.order.dto.OrderAddRequest;

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
    //
    // @GetMapping("/{orderId}")
    // public ResponseEntity<Orders> findOrder(@PathVariable final String customerName,
    //                                         @PathVariable final Long orderId) {
    //     final Orders order = orderService.findOrderById(customerName, orderId);
    //     return ResponseEntity.ok(order);
    // }
    //
    // @GetMapping
    // public ResponseEntity<List<Orders>> findOrders(@PathVariable final String customerName) {
    //     final List<Orders> orders = orderService.findOrdersByCustomerName(customerName);
    //     return ResponseEntity.ok(orders);
    // }
}