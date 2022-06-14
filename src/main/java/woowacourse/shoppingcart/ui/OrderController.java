package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/customer/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@AuthenticationPrincipal final Long customerId,
                                    @RequestBody @Valid final List<OrderRequest> orderRequests) {
        final Long orderId = orderService.addOrder(customerId, orderRequests);

        return ResponseEntity.created(URI.create("/api/customer/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOne(@AuthenticationPrincipal final Long customerId,
                                                @PathVariable final Long orderId) {
        final OrderResponse orderResponseDto = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(orderResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll(@AuthenticationPrincipal final Long customerId) {
        final List<OrderResponse> orderResponses = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orderResponses);
    }
}
