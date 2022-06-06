package woowacourse.shoppingcart.controller;

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
import woowacourse.auth.controller.CustomerId;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.service.OrderService;

@Validated
@RestController
@RequestMapping("/api/customers/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@CustomerId final Long customerId,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, customerId);
        return ResponseEntity.created(
                URI.create("/api/customers/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findOrder(@CustomerId final Long customerId, @PathVariable final Long orderId) {
        final Orders order = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(@CustomerId final Long customerId) {
        final List<Orders> orders = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }
}
