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

import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.order.Order;
import woowacourse.shoppingcart.dto.OrderResponse;

@RestController
@RequestMapping("/api/customers")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{customerId}/orders")
    public ResponseEntity<Void> create(@PathVariable long customerId) {
        Long savedId = orderService.save(customerId);
        return ResponseEntity.created(URI.create("/api/customers/" + customerId + "/orders/" + savedId)).build();
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderResponse>> findAll(@PathVariable long customerId) {
        List<Order> orders = orderService.findAll(customerId);
        List<OrderResponse> orderResponses = orders.stream()
            .map(OrderResponse::from)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(orderResponses);
    }
}
