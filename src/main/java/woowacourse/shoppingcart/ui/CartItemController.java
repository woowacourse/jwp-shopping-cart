package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.dto.cart.CartItemRequest;
import woowacourse.shoppingcart.dto.cart.CartItemResponse;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.cart.CartsResponse;
import woowacourse.shoppingcart.dto.cart.RemovedCartItemsRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartsResponse> findCartItems(@AuthenticationPrincipal Long customerId) {
        CartsResponse cartsResponse = cartService.findCartItems(customerId);
        return ResponseEntity.ok().body(cartsResponse);
    }



    @PostMapping
    public ResponseEntity<CartItemResponse> addCartItem(@AuthenticationPrincipal Long customerId, @RequestBody CartItemRequest cartItemRequest) {
        CartItemResponse cartResponse = cartService.addCartItem(customerId, cartItemRequest);
        return ResponseEntity.created(URI.create("/customers/carts")).body(cartResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeCartItems(@AuthenticationPrincipal Long customerId, @RequestBody RemovedCartItemsRequest removedCartItemsRequest) {
        cartService.removeCartItems(customerId, removedCartItemsRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> editCartItem(@AuthenticationPrincipal Long customerId, @RequestBody CartItemRequest cartItemRequest) {
        cartService.editCartItem(customerId, cartItemRequest);
        return ResponseEntity.ok().build();
    }
}
