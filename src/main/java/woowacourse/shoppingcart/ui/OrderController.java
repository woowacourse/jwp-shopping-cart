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
import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.ui.dto.FindCustomerRequest;
import woowacourse.shoppingcart.ui.dto.OrderDetailRequest;

@Validated
@RestController
@RequestMapping("/api/customers/me/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal FindCustomerRequest request,
        @RequestBody @Valid final List<OrderDetailRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, request.getId());
        return ResponseEntity.created(
            URI.create("/api/customers/me/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal FindCustomerRequest request,
        @PathVariable final Long orderId) {
        final OrderResponse orderResponse = orderService.findOrderById(request.getId(), orderId);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findOrders(@AuthenticationPrincipal FindCustomerRequest request) {
        return ResponseEntity.ok(orderService.findOrdersByCustomerId(request.getId()));
    }
}
