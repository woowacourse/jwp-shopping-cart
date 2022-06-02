package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.application.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/customers/me/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(HttpServletRequest request,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(request, orderDetails);
        return ResponseEntity.created(
                URI.create("/api/me/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findOrder(HttpServletRequest request,
                                            @PathVariable final Long orderId) {

        final Orders order = orderService.findOrderById(request, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(HttpServletRequest request) {
        final List<Orders> orders = orderService.findOrders(request);
        return ResponseEntity.ok(orders);
    }
}
