package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/customers/me/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                   @RequestBody @Valid List<OrderRequest> orderDetails) {
        Long orderId = orderService.addOrder(orderDetails, loginCustomer);
        return ResponseEntity.created(
                URI.create("/api/customers/me/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findOrder(@AuthenticationPrincipal LoginCustomer loginCustomer, @PathVariable Long orderId) {
        Orders order = orderService.findOrderById(loginCustomer, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        List<Orders> orders = orderService.findOrdersByCustomerName(loginCustomer);
        return ResponseEntity.ok(orders);
    }
}
