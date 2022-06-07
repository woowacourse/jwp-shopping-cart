package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.OrdersResponse;
import woowacourse.shoppingcart.dto.OrderRequest;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/members/me/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal Long memberId,
                                         @RequestBody @Valid List<OrderRequest> orderDetails) {
        Long orderId = orderService.addOrder(orderDetails, memberId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + orderId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal Long memberId,
                                                    @PathVariable Long orderId) {
        OrdersResponse order = orderService.findOrder(memberId, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@AuthenticationPrincipal Long memberId) {
        List<OrdersResponse> orders = orderService.findOrders(memberId);
        return ResponseEntity.ok(orders);
    }
}
