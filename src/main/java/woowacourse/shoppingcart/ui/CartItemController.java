package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemDeleteRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemResponse;
import woowacourse.shoppingcart.dto.cartItem.CartItemUpdateRequest;

@RestController
@RequestMapping("/api/mycarts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal final String email) {
        List<CartItemResponse> cartItemResponses = cartService.findCartsByEmail(email);
        return ResponseEntity.ok().body(cartItemResponses);
    }

    @GetMapping({"/{cartId}"})
    public ResponseEntity<CartItemResponse> getCartItem(@AuthenticationPrincipal final String email,
                                                        @PathVariable final Long cartId) {
        CartItemResponse cartItemResponse = cartService.findCart(cartId);
        return ResponseEntity.ok().body(cartItemResponse);
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addCartItem(@RequestBody final CartItemAddRequest cartItemAddRequest,
                                                        @AuthenticationPrincipal final String email) {
        final CartItemResponse cartItemResponse = cartService.addCart(cartItemAddRequest.getProductId(),
                cartItemAddRequest.getQuantity(), email);

        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartItemResponse.getId())
                .toUri();

        return ResponseEntity.created(responseLocation).body(cartItemResponse);
    }

    @PatchMapping
    public ResponseEntity<Void> updateCartItem(@RequestBody final CartItemUpdateRequest cartItemUpdateRequest,
                                               @AuthenticationPrincipal final String email) {
        cartService.updateCart(email, cartItemUpdateRequest.getCartItemId(), cartItemUpdateRequest.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@RequestBody final CartItemDeleteRequest cartItemDeleteRequest,
                                               @AuthenticationPrincipal final String email) {
        cartService.deleteCarts(email, cartItemDeleteRequest.getCartItemIds());
        return ResponseEntity.noContent().build();
    }
}
