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
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartItemIds;
import woowacourse.shoppingcart.dto.OrdersDto;
import woowacourse.shoppingcart.service.OrderService;

@Validated
@RestController
@RequestMapping("/api/myorders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal Customer customer,
            @RequestBody final CartItemIds cartItemIds) {
        final Long orderId = orderService.addOrder(customer, cartItemIds.getCartItemIds());
        return ResponseEntity.created(URI.create("/api/myorders/" + orderId)).build();
    }

    @GetMapping
    public ResponseEntity<List<OrdersDto>> findOrders(@AuthenticationPrincipal Customer customer) {
        final List<OrdersDto> orders = orderService.findOrders(customer);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrdersDto> findOrder(@AuthenticationPrincipal Customer customer,
            @PathVariable final Long orderId) {
        final OrdersDto ordersDto = orderService.findOrderDetails(customer, orderId);
        return ResponseEntity.ok(ordersDto);
    }
}
