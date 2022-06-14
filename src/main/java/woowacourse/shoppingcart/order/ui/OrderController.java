package woowacourse.shoppingcart.order.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.order.application.OrderService;
import woowacourse.shoppingcart.order.domain.Orders;
import woowacourse.shoppingcart.order.dto.OrderCreationRequest;
import woowacourse.shoppingcart.order.dto.OrderResponse;
import woowacourse.shoppingcart.support.Login;

@RestController
@RequestMapping("/users/me/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@Login final Customer customer,
                                         @RequestBody @Valid final List<OrderCreationRequest> requests) {
        final Long orderId = orderService.addOrder(requests, customer);
        return ResponseEntity
                .created(URI.create("/users/me/orders/" + orderId))
                .build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@Login final Customer customer,
                                                   @PathVariable final Long orderId) {
        final Orders order = orderService.findOrderById(customer, orderId);
        final OrderResponse response = OrderResponse.from(order);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findOrders(@Login final Customer customer) {
        final List<OrderResponse> response = orderService.findAllOrders(customer)
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
