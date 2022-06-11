package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.domain.User.User;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.OrderRequest;

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
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final User user,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, user);
        return ResponseEntity.created(
                URI.create("/api/customers/me/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findOrder(@AuthenticationPrincipal final User user,
                                            @PathVariable final Long orderId) {
        final Orders order = orderService.findOrderById(user, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(@AuthenticationPrincipal final User user) {
        final List<Orders> orders = orderService.findOrdersByCustomerName(user);
        return ResponseEntity.ok(orders);
    }
}