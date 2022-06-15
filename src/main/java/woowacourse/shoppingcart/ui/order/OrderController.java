package woowacourse.shoppingcart.ui.order;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
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
import woowacourse.shoppingcart.application.dto.OrderDetailsServiceResponse;
import woowacourse.shoppingcart.ui.order.dto.request.OrderRequest;
import woowacourse.shoppingcart.ui.order.dto.response.OrderDetailsResponse;

@Validated
@RestController
@RequestMapping("/api/customer/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@AuthenticationPrincipal final Long customerId,
                                     @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(customerId, orderDetails);
        return ResponseEntity.created(URI.create("/api/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponse> findOrder(@AuthenticationPrincipal final Long customerId,
                                                          @PathVariable final Long orderId) {
        final OrderDetailsServiceResponse orderDetailServiceResponse = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(OrderDetailsResponse.from(orderDetailServiceResponse));
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailsResponse>> findOrders(@AuthenticationPrincipal final Long customerId) {
        final List<OrderDetailsServiceResponse> serviceResponses = orderService.findOrdersByCustomerId(customerId);
        final List<OrderDetailsResponse> orderDetailsResponses = serviceResponses.stream()
                .map(OrderDetailsResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDetailsResponses);
    }
}