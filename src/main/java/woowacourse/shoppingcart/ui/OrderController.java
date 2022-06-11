package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal String userNameByToken,
                                         @RequestBody @Valid List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, userNameByToken);
        return ResponseEntity.created(URI.create("/orders/" + orderId)).build();
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@AuthenticationPrincipal String userNameByToken) {
        final List<OrdersResponse> orders = orderService.findOrdersByCustomerName(userNameByToken);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal String userNameByToken,
                                                    @PathVariable final Long orderId) {
        final OrdersResponse order = orderService.findOrderById(userNameByToken, orderId);
        return ResponseEntity.ok(order);
    }
}
