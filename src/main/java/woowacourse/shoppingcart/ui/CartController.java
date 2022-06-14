package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.cart.CartRequest;
import woowacourse.shoppingcart.dto.cart.CartQuantityResponse;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.cart.CartsResponse;
import woowacourse.shoppingcart.dto.cart.RemovedCartsRequest;

import java.net.URI;

@RestController
@RequestMapping("/customers/carts")
public class CartController {
    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartsResponse> findCarts(@AuthenticationPrincipal Long customerId) {
        CartsResponse cartsResponse = cartService.findCarts(customerId);
        return ResponseEntity.ok().body(cartsResponse);
    }



    @PostMapping
    public ResponseEntity<CartQuantityResponse> addCart(@AuthenticationPrincipal Long customerId, @RequestBody CartRequest cartRequest) {
        CartQuantityResponse cartResponse = cartService.addCart(customerId, cartRequest);
        return ResponseEntity.created(URI.create("/customers/carts")).body(cartResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeCarts(@AuthenticationPrincipal Long customerId, @RequestBody RemovedCartsRequest removedCartsRequest) {
        cartService.removeCarts(customerId, removedCartsRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> editCart(@AuthenticationPrincipal Long customerId, @RequestBody CartRequest cartRequest) {
        cartService.editCart(customerId, cartRequest);
        return ResponseEntity.ok().build();
    }
}
