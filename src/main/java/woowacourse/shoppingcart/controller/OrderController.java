package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.service.OrderService;

@RestController
@RequestMapping("/api/myorders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrders(@AuthenticationPrincipal String email,
        @RequestBody OrderRequest orderRequest) {
        long orderId = orderService.addOrder(email, orderRequest);
        return ResponseEntity.created(URI.create("/api/myorders/" + orderId)).build();
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findOrders(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(orderService.findOrdersByCustomer(email));
    }
}
