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
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

@Validated
@RestController
@RequestMapping("/api/customers/me/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final LoginCustomer loginCustomer,
                                         @RequestBody @Valid final List<OrderRequest> orderRequests) {
        final Long orderId = orderService.addOrder(orderRequests, loginCustomer.getId());
        return ResponseEntity.created(URI.create("/api/customers/me/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal final LoginCustomer loginCustomer,
                                                    @PathVariable final Long orderId) {
        final OrdersResponse ordersResponse = orderService.findOrderById(loginCustomer.getId(), orderId);
        return ResponseEntity.ok(ordersResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@AuthenticationPrincipal final LoginCustomer loginCustomer) {
        final List<OrdersResponse> ordersByCustomerId = orderService.findOrdersByCustomerId(loginCustomer.getId());
        return ResponseEntity.ok(ordersByCustomerId);
    }
}
