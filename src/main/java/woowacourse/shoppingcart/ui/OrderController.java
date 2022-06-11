package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers/me/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final Customer customer,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, customer.getUserName());
        return ResponseEntity.created(
                URI.create("/api/customers/me/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal final Customer customer,
                                                    @PathVariable final Long orderId) {
        final OrdersResponse order = orderService.findById(customer.getUserName(), orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@AuthenticationPrincipal final Customer customer) {
        final List<OrdersResponse> orders = orderService.findByCustomerName(customer.getUserName());
        return ResponseEntity.ok(orders);
    }
}
