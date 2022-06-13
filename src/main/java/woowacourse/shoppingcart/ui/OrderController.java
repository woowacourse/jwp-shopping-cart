package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.MemberOnly;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;

@Validated
@RestController
@RequestMapping("/customers/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@MemberOnly Long customerId,
                                         @RequestBody @Valid final OrderRequest orderRequest) {
        final Long orderId = orderService.addOrder(customerId, orderRequest);
        return ResponseEntity.created(
                URI.create("/customers/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findOrder(@MemberOnly Long customerId, @PathVariable Long orderId) {
        return orderService.findOrderById(customerId, orderId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> findOrders(@MemberOnly Long customerId) {
        return orderService.findOrdersByCustomerId(customerId);
    }
}