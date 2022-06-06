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
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;

@Validated
@RestController
@RequestMapping
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/members/me/orders")
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final Long memberId,
                                   @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(memberId, orderDetails);
        return ResponseEntity.created(
                URI.create("/api/members/me/orders/" + orderId)).build();
    }

    @GetMapping("/api/members/me/orders/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal final Long memberId,
                                                   @PathVariable final Long orderId) {
        final OrderResponse orderResponse = orderService.findOrderById(memberId, orderId);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/api/members/me/orders")
    public ResponseEntity<List<OrderResponse>> findOrders(@AuthenticationPrincipal final Long memberId) {
        List<OrderResponse> orderResponses = orderService.findOrdersByMemberId(memberId);
        return ResponseEntity.ok(orderResponses);
    }
}
