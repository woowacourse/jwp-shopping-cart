package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.CartResponses;
import woowacourse.shoppingcart.dto.DeleteCartItemRequests;
import woowacourse.shoppingcart.dto.UpdateCartItemRequests;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponses> getCartItems(@AuthenticationPrincipal final String username) {
        return ResponseEntity.ok().body(cartService.findCartsByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@RequestBody final AddCartItemRequest addCartItemRequest,
                                            @AuthenticationPrincipal final String username) {
        final Long cartId = cartService.addCart(addCartItemRequest, username);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @PatchMapping
    public ResponseEntity<CartResponses> updateCartItems(
            @RequestBody UpdateCartItemRequests updateCartItemRequests,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok().body(cartService.updateCartItems(updateCartItemRequests, username));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItems(@AuthenticationPrincipal final String username,
                                                @RequestBody DeleteCartItemRequests deleteCartItemRequests) {
        cartService.deleteAllCartByProducts(username, deleteCartItemRequests);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteCartItems(@AuthenticationPrincipal final String username) {
        cartService.deleteAllCart(username);
        return ResponseEntity.noContent().build();
    }
}
