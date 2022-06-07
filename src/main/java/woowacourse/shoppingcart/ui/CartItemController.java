package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartItemQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;

@RestController
@RequestMapping("/api/mycarts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal String email) {
        final List<CartItemResponse> cartItemsResponse = cartService.findCartItems(email);
        return ResponseEntity.ok(cartItemsResponse);
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> getCartItem(@PathVariable Long cartItemId, @AuthenticationPrincipal String email) {
        final CartItemResponse cartItemResponse = cartService.findCartItem(email, cartItemId);
        return ResponseEntity.ok(cartItemResponse);
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addCartItem(@Valid @RequestBody CartItemRequest cartItemRequest, @AuthenticationPrincipal String email) {
        final CartItemResponse cartItemResponse = cartService
                .addCartItem(cartItemRequest.getProductId(), cartItemRequest.getQuantity(), email);
        return ResponseEntity.created(URI.create("/api/mycarts/" + cartItemResponse.getId())).body(cartItemResponse);
    }

    @PatchMapping
    public ResponseEntity<Void> updateCartItemQuantity(@Valid @RequestBody CartItemQuantityRequest cartItemQuantityRequest,
                                                       @AuthenticationPrincipal String email) {
        cartService.updateQuantity(email, cartItemQuantityRequest.getCartItemId(), cartItemQuantityRequest.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@RequestBody DeleteCartItemRequest deleteCartItemRequest,
                                               @AuthenticationPrincipal String email) {
        final List<Long> cartItemIds = deleteCartItemRequest.getCartItemIds();
        cartService.deleteCartItem(email, cartItemIds);
        return ResponseEntity.noContent().build();
    }
}
