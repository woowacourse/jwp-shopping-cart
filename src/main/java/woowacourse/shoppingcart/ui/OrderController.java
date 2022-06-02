package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.domain.Orders;
import woowacourse.shoppingcart.application.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/customers/me/orders")
public class OrderController {
    private final AuthService authService;
    private final OrderService orderService;

    public OrderController(final AuthService authService, final OrderService orderService) {
        this.authService = authService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(HttpServletRequest request,
                                         @RequestBody @Valid final List<OrderRequest> orderDetails) {
        String customerName = getNameFromToken(request);
        final Long orderId = orderService.addOrder(orderDetails, customerName);
        return ResponseEntity.created(
                URI.create("/api/me/orders/" + orderId)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> findOrder(HttpServletRequest request,
                                            @PathVariable final Long orderId) {

        String customerName = getNameFromToken(request);
        final Orders order = orderService.findOrderById(customerName, orderId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Orders>> findOrders(HttpServletRequest request) {
        String customerName = getNameFromToken(request);
        final List<Orders> orders = orderService.findOrdersByCustomerName(customerName);
        return ResponseEntity.ok(orders);
    }

    private String getNameFromToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        return authService.getNameFromToken(token);
    }
}
