package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.controller.CustomerId;
import woowacourse.shoppingcart.dto.request.OrdersRequest;
import woowacourse.shoppingcart.dto.response.OrdersResponse;
import woowacourse.shoppingcart.service.OrdersService;

@Validated
@RestController
@RequestMapping("/api/customers/orders")
public class OrderController {
    private final OrdersService ordersService;

    public OrderController(final OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@CustomerId final Long customerId,
                                         @RequestBody final List<OrdersRequest> orderDetails) {
        final Long orderId = ordersService.addOrder(orderDetails, customerId);
        return ResponseEntity.created(URI.create("/api/customers/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersResponse> findOrder(@CustomerId final Long customerId,
                                                    @PathVariable final Long orderId) {
        OrdersResponse ordersResponse = ordersService.findOrdersById(customerId, orderId);
        return ResponseEntity.ok(ordersResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrdersResponse>> findOrders(@CustomerId final Long customerId) {
        List<OrdersResponse> ordersRespons = ordersService.findOrdersByCustomerId(customerId);
        return ResponseEntity.ok(ordersRespons);
    }
}
