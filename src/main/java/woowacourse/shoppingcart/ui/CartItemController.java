package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerEmail(email));
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal String email, @PathVariable Long productId) {
        Long cartId = cartService.addCart(email, productId);
        return ResponseEntity.created(URI.create("/api/carts/" + cartId)).build();
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @AuthenticationPrincipal String email,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateQuantityRequest request) {
        cartService.updateQuantity(email, productId, request.getQuantity());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal String email, @PathVariable Long productId) {
        cartService.deleteCartItem(email, productId);
        return ResponseEntity.noContent().build();
    }
}
