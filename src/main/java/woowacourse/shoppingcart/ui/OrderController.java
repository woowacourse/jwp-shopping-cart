package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.request.LoginCustomer;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.application.OrderService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

@Validated
@RestController
@RequestMapping("/customers/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                   @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, loginCustomer.getUsername());
        return ResponseEntity.created(URI.create("/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                            @PathVariable final Long orderId) {
        final OrdersResponse ordersResponse = orderService.findOrderById(loginCustomer.getUsername(), orderId);
        return ResponseEntity.ok(ordersResponse);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        final List<Orders> orders = orderService.findOrdersByCustomerName(loginCustomer.getUsername());
        return ResponseEntity.ok(orders);
    }
}
