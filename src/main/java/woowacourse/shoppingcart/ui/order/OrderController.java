package woowacourse.shoppingcart.ui.order;

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
import woowacourse.shoppingcart.application.dto.OrderDetailServiceResponse;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.ui.order.dto.request.OrderRequest;

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
        return ResponseEntity.created(
                URI.create("/api/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailServiceResponse> findOrder(@AuthenticationPrincipal final Long customerId,
                                                                @PathVariable final Long orderId) {
        final OrderDetailServiceResponse orderDetailServiceResponse = orderService.findOrderById(customerId, orderId);
        return ResponseEntity.ok(orderDetailServiceResponse);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(@AuthenticationPrincipal final Long customerId) {
        final List<Orders> orders = orderService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }
}