package woowacourse.shoppingcart.order.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.order.application.OrderService;
import woowacourse.shoppingcart.order.dto.OrderRequest;
import woowacourse.shoppingcart.order.dto.OrderResponse;

@Validated
@RestController
@RequestMapping("/api/myorders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@RequestBody OrderRequest orderRequest,
                                         @AuthenticationPrincipal String email) {
        final List<Long> cartItemIds = orderRequest.getCartItemIds();
        final Long orderId = orderService.addOrder(cartItemIds, email);
        return ResponseEntity.created(URI.create("/api/myorders/" + orderId)).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findOrders(@AuthenticationPrincipal String email) {
        final List<OrderResponse> orderResponses = orderService.findOrdersByEmail(email);
        return ResponseEntity.ok(orderResponses);
    }
}
