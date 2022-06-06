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
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.dto.OrderRequest;

@Validated
@RestController
@RequestMapping
public class OrderController {
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/customers/{customerName}/orders")
    public ResponseEntity<Void> addOrder(@PathVariable final String customerName,
                                   @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, customerName);
        return ResponseEntity.created(
                URI.create("/api/" + customerName + "/orders/" + orderId)).build();
    }

    @PostMapping("/api/members/me/orders")
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final Long memberId,
                                   @RequestBody @Valid final List<OrderRequest> orderDetails) {
        final Long orderId = orderService.addOrder(memberId, orderDetails);
        return ResponseEntity.created(
                URI.create("/api/members/me/orders/" + orderId)).build();
    }

    @GetMapping("/api/customers/{customerName}/orders/{orderId}")
    public ResponseEntity<Orders> findOrder(@PathVariable final String customerName,
                                            @PathVariable final Long orderId) {
        final Orders order = orderService.findOrderById(customerName, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/api/customers/{customerName}/orders")
    public ResponseEntity<List<Orders>> findOrders(@PathVariable final String customerName) {
        final List<Orders> orders = orderService.findOrdersByCustomerName(customerName);
        return ResponseEntity.ok(orders);
    }
}
