package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.dto.request.CartItemsRequest;

@Validated
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(final OrderService orderService, final CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrder(@AuthenticationPrincipal final String username,
                                         @RequestBody @Valid final CartItemsRequest cartItemsRequest) {
        //final Long orderId = orderService.addOrder(orderRequest, username);
        cartService.deleteCartItems(cartItemsRequest, username);
        return ResponseEntity.created(
                URI.create("/orders/" + 1)).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Void> findOrder(@PathVariable final String customerName,
                                          @PathVariable final Long orderId) {
        //final Orders order = orderService.findOrderById(customerName, orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Void> findOrders(@PathVariable final String customerName) {
        //final List<Orders> orders = orderService.findOrdersByCustomerName(customerName);
        return ResponseEntity.ok().build();
    }
}
