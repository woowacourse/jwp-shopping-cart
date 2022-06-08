package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.Login;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.domain.order.Orders;
import woowacourse.shoppingcart.dto.order.MakeOrderResponse;
import woowacourse.shoppingcart.dto.order.OrderSaveRequest;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<MakeOrderResponse> addOrder(@Login String email, @RequestBody OrderSaveRequest orderSaveRequest) {
        MakeOrderResponse makeOrderResponse = orderService.addOrder(orderSaveRequest, email);
        return ResponseEntity.created(createUri(makeOrderResponse.getId())).body(makeOrderResponse);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findOrder(@PathVariable String customerName,
                                            @PathVariable Long orderId) {
        Orders order = orderService.findOrderById(customerName, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(@PathVariable String customerName) {
        List<Orders> orders = orderService.findOrdersByCustomerName(customerName);
        return ResponseEntity.ok(orders);
    }

    private URI createUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id)
                .build().toUri();
    }
}
