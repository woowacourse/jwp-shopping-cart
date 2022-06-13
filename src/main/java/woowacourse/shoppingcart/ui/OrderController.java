package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/customers/{customerName}/orders")
    public ResponseEntity<Void> addOrder(@PathVariable final String customerName,
                                   @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, customerName);
        return ResponseEntity.created(
                URI.create("/api/" + customerName + "/orders/" + orderId)).build();
    }

    @GetMapping("/api/customers/{customerName}/orders/{orderId}")
    public ResponseEntity<Orders> findOrder(@PathVariable final String customerName,
                                            @PathVariable final Long orderId) {
        final Orders order = orderService.findOrderById(customerName, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/api/customers/orders")
    public ResponseEntity<List<Orders>> findOrders(@AuthenticationPrincipal String email) {
        final List<Orders> orders = orderService.findOrdersByCustomerEmail(email);
        return ResponseEntity.ok(orders);
    }
}