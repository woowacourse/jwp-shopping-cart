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
import woowacourse.shoppingcart.dto.order.OrderCreateRequest;
import woowacourse.shoppingcart.dto.order.OrderResponse;

@Validated
@RestController
@RequestMapping("/api/myorders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final String email,
                                         @RequestBody @Valid final OrderCreateRequest orderCreateRequest) {
        final Long orderId = orderService.addOrder(orderCreateRequest.getCartItemIds(), email);
        return ResponseEntity.created(URI.create("/api/myorders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal final String email,
                                                   @PathVariable final Long orderId) {
        final OrderResponse order = orderService.findOrderById(email, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findOrders(@AuthenticationPrincipal final String email) {
        final List<OrderResponse> orders = orderService.findOrdersByCustomerEmail(email);
        return ResponseEntity.ok(orders);
    }
}