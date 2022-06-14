package woowacourse.shoppingcart.order.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.auth.support.jwt.AuthenticationPrincipal;
import woowacourse.shoppingcart.order.application.OrderService;
import woowacourse.shoppingcart.order.application.dto.request.OrderRequest;
import woowacourse.shoppingcart.order.application.dto.response.OrderResponse;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(@AuthenticationPrincipal final Long customerId,
                                                  @RequestBody final OrderRequest orderRequest) {
        final long orderId = orderService.addOrder(customerId, orderRequest);
        final OrderResponse orderResponse = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.created(
                URI.create("/orders/" + orderResponse.getId())).body(orderResponse);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal final Long customerId,
                                                   @PathVariable final Long orderId) {
        final OrderResponse orderResponse = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(orderResponse);
    }
}