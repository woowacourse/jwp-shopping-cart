package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(@AuthenticationPrincipal final Long customerId,
                                                  @Valid @RequestBody final OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.addOrder(customerId, orderRequest);
        return ResponseEntity.created(URI.create("/orders/" + orderResponse.getId())).body(orderResponse);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@AuthenticationPrincipal final Long customerId,
                                                   @PathVariable final Long orderId) {
        OrderResponse orderResponse = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(orderResponse);
    }
}
