package woowacourse.shoppingcart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.shoppingcart.application.OrderService;

@RestController
@RequestMapping("/api/customers")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{customerId}/orders")
    public ResponseEntity<Void> addCartItem(@PathVariable long customerId) {
        Long savedId = orderService.save(customerId);
        return ResponseEntity.created(URI.create("/api/customers/" + customerId + "/orders/" + savedId)).build();
    }
}
