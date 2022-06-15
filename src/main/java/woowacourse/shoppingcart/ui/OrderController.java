package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.request.OrderRequest;
import woowacourse.shoppingcart.dto.response.OrdersResponse;

@RestController
@RequestMapping("/api/customer/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal Long customerId,
                                         @Valid @RequestBody final List<OrderRequest> orderRequests) {
        final Long orderId = orderService.addOrder(orderRequests, customerId);

        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(orderId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@AuthenticationPrincipal Long customerId) {
        final List<OrdersResponse> orders = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal Long customerId,
                                                    @PathVariable final Long orderId) {
        final OrdersResponse order = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(order);
    }
}
