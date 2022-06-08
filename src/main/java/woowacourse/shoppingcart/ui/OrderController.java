package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.Orders;
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
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final UserName userName,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, userName);
        return ResponseEntity.created(
                URI.create("/api/customers/" + userName.value() + "/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findOrder(@AuthenticationPrincipal final UserName userName,
                                            @PathVariable final Long orderId) {
        final Orders order = orderService.findOrderById(userName, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(@AuthenticationPrincipal final UserName userName) {
        final List<Orders> orders = orderService.findOrdersByCustomerName(userName);
        return ResponseEntity.ok(orders);
    }
}