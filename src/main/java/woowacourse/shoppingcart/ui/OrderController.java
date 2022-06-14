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
import woowacourse.auth.config.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;

@Validated
@RestController
@RequestMapping("/customers/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final LoginCustomer loginCustomer,
            @Valid @RequestBody final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, loginCustomer.getUsername());
        return ResponseEntity.created(
                URI.create("/customers/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal final LoginCustomer loginCustomer,
            @PathVariable final Long orderId) {
        final OrdersResponse ordersResponse = orderService.findOrderById(loginCustomer.getUsername(), orderId);
        return ResponseEntity.ok(ordersResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@AuthenticationPrincipal final LoginCustomer loginCustomer) {
        final List<OrdersResponse> orders = orderService.findOrdersByCustomerName(loginCustomer.getUsername());
        return ResponseEntity.ok(orders);
    }
}