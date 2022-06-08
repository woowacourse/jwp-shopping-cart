package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
        final Long orderId = orderService.addOrder(cartItemsRequest, username);
        cartService.deleteCartItems(cartItemsRequest, username);
        return ResponseEntity.created(URI.create("/orders/" + orderId)).build();
    }
}
