package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.application.dto.OrderResponse;
import woowacourse.shoppingcart.ui.dto.OrderRequest;
import woowacourse.shoppingcart.ui.dto.OrdersResponse;

@RestController
@RequestMapping("/customers/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@AuthenticationPrincipal Long customerId,
                                    @RequestBody OrderRequest orderRequest) {
        final Long orderId = orderService.save(orderRequest, customerId);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(orderId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping("/{orderId}")
    public OrderResponse findOrder(@AuthenticationPrincipal Long customerId,
                                   @PathVariable final Long orderId) {
        return orderService.findById(customerId, orderId);
    }

    @GetMapping
    public OrdersResponse findAll(@AuthenticationPrincipal Long customerId) {
        return new OrdersResponse(orderService.findByCustomerId(customerId));
    }
}
