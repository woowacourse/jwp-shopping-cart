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
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;

@Validated
@RestController
@RequestMapping("/customers/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal Long id,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(id, orderDetails);
        return ResponseEntity.created(
                URI.create("/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findOrder(@AuthenticationPrincipal Long id,
                                            @PathVariable final Long orderId) {
        final Orders order = orderService.findOrderById(id, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(@AuthenticationPrincipal Long id) {
        final List<Orders> orders = orderService.findOrdersByCustomerId(id);
        return ResponseEntity.ok(orders);
    }
}
