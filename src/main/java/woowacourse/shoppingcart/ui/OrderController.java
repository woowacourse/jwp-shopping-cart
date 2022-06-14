package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersResponse;

import javax.validation.Valid;
import java.net.URI;

@Validated
@RestController
@RequestMapping("/customers/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final long customerId,
                                         @RequestBody @Valid final OrderRequest ordersRequest) {
        final long orderId = orderService.addOrder(ordersRequest, customerId);

        final URI responseUri = UriCreator.withCurrentPath(String.format("%d", orderId));

        return ResponseEntity.created(responseUri)
                .build();
    }

    @GetMapping("/{orderId}")
    public OrderResponse findOrder(@AuthenticationPrincipal final long customerId,
                                   @PathVariable final long orderId) {
        return orderService.findOrderById(customerId, orderId);
    }

    @GetMapping
    public OrdersResponse findOrders(@AuthenticationPrincipal final long customerId) {
        return orderService.findOrdersByCustomerId(customerId);
    }
}
