package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.domain.user.Customer;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.request.OrderRequest;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(Customer authCustomer,
                                            @RequestBody OrderRequest requestBody) {
        Long orderId = orderService.buyCartItems(authCustomer, requestBody.getProductIds());
        URI location = URI.create("/orders/" + orderId);
        return ResponseEntity.created(location).build();
    }
}
