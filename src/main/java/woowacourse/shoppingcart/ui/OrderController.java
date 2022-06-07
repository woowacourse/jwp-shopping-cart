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

import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;

@Validated
@RestController
@RequestMapping("/api/customers/{customerId}/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@PathVariable final long customerId,
        @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, customerId);
        return ResponseEntity.created(
            URI.create("/api/" + customerId + "/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findOrder(@PathVariable final long customerId,
        @PathVariable final Long orderId) {
        final Orders order = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(@PathVariable final long customerId) {
        final List<Orders> orders = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }
}
