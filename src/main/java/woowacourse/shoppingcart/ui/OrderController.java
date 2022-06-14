package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.dto.OrderResponse;

@RestController
@RequestMapping("/api/customers")
public class OrderController {

    private final OrderService orderService;
    private final AuthorizationValidator authorizationValidator;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.authorizationValidator = new AuthorizationValidator();
    }

    @PostMapping("/{customerId}/orders")
    public ResponseEntity<Void> create(@PathVariable long customerId, @AuthenticationPrincipal Customer customer) {
        authorizationValidator.validate(customerId, customer);
        Long savedId = orderService.save(customerId);
        return ResponseEntity.created(URI.create("/api/customers/" + customerId + "/orders/" + savedId)).build();
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderResponse>> findAll(@PathVariable long customerId,
        @AuthenticationPrincipal Customer customer) {
        authorizationValidator.validate(customerId, customer);
        List<Order> orders = orderService.findAll(customerId);
        List<OrderResponse> orderResponses = orders.stream()
            .map(OrderResponse::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(orderResponses);
    }

    @GetMapping("/{customerId}/orders/{orderId}")
    public ResponseEntity<OrderResponse> findOne(@PathVariable long customerId, @PathVariable long orderId,
        @AuthenticationPrincipal Customer customer) {
        authorizationValidator.validate(customerId, customer);
        Order order = orderService.findOne(customerId, orderId);
        OrderResponse orderResponse = OrderResponse.from(order);
        return ResponseEntity.ok().body(orderResponse);
    }
}
