package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.request.CartItemsRequest;
import woowacourse.shoppingcart.dto.request.QuantityRequest;
import woowacourse.shoppingcart.dto.response.CartItemsResponse;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartItemsResponse> getCartItems(@AuthenticationPrincipal final String username) {
        CartItemsResponse cartItemsResponse = cartService.findCartsByUsername(username);
        return ResponseEntity.ok().body(cartItemsResponse);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final String username,
                                            @PathVariable final Long productId) {
        cartService.addCart(productId, username);
        return ResponseEntity.created(URI.create("/cart")).build();
    }

    @PutMapping("/{productId}/quantity")
    public ResponseEntity<Void> updateQuantity(@AuthenticationPrincipal final String username,
                                               @RequestBody final QuantityRequest quantityRequest,
                                               @PathVariable final Long productId) {
        cartService.updateQuantity(productId, quantityRequest, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteCartItems(@AuthenticationPrincipal final String username,
                                                @RequestBody final CartItemsRequest cartItemsRequest) {
        cartService.deleteCartItems(cartItemsRequest, username);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal final String username) {
        cartService.deleteCart(username);
        return ResponseEntity.noContent().build();
    }
}
