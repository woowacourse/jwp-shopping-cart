package woowacourse.shoppingcart.controller;

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
import woowacourse.auth.support.UserNameResolver;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.CreateOrderDetailRequest;
import woowacourse.shoppingcart.dto.response.OrderResponse;
import woowacourse.shoppingcart.service.OrderService;

@Validated
@RestController
@RequestMapping("/api/customers/me/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@UserNameResolver final UserName customerName,
                                         @RequestBody @Valid final List<CreateOrderDetailRequest> orderDetails) {
        final Long orderId = orderService.addOrder(orderDetails, customerName);
        return ResponseEntity.created(
                URI.create("/api/" + customerName.getValue() + "/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrder(@UserNameResolver final UserName customerName,
                                                   @PathVariable final Long orderId) {
        return ResponseEntity.ok(orderService.findOrderById(customerName, orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findOrders(@UserNameResolver final UserName customerName) {
        return ResponseEntity.ok(orderService.findOrdersByCustomerName(customerName));
    }
}
