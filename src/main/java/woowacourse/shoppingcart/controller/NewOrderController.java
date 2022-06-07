package woowacourse.shoppingcart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.OrderResponse;
import woowacourse.shoppingcart.service.OrderService;

@RestController
@RequestMapping("/api/myorders")
public class NewOrderController {

    private final OrderService orderService;

    public NewOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findOrders(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(orderService.findOrdersByCustomer(email));
    }
}
