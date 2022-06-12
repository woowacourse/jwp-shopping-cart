package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.application.dto.OrderServiceRequest;
import woowacourse.shoppingcart.ui.dto.OrderRequest;
import woowacourse.shoppingcart.ui.dto.OrdersResponse;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/members/me/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal long memberId,
                                         @RequestBody List<OrderRequest> orders) {
        List<OrderServiceRequest> orderServiceRequests = convertOrderServiceRequest(orders);
        long orderId = orderService.addOrder(orderServiceRequests, memberId);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + orderId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    private List<OrderServiceRequest> convertOrderServiceRequest(List<OrderRequest> orders) {
        return orders.stream()
                .map(OrderRequest::toServiceRequest)
                .collect(Collectors.toList());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@AuthenticationPrincipal long memberId,
                                                    @PathVariable long orderId) {
        OrdersResponse order = orderService.findOrder(memberId, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@AuthenticationPrincipal long memberId) {
        List<OrdersResponse> orders = orderService.findOrders(memberId);
        return ResponseEntity.ok(orders);
    }
}
