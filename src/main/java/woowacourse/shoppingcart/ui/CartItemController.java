package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemResponse;
import woowacourse.shoppingcart.dto.cartItem.CartItemsResponse;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartItemsResponse> getCartItems(@AuthenticationPrincipal Long customerId) {
        return ResponseEntity.ok().body(cartService.findAllByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addCartItem(@Valid @RequestBody final CartItemAddRequest request,
                                                        @AuthenticationPrincipal Long customerId) {
        final Long cartItemId = cartService.add(customerId, request);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartItemId}")
                .buildAndExpand(cartItemId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> getCartItem(@PathVariable final Long cartItemId,
                                               @AuthenticationPrincipal Long customerId) {
        return ResponseEntity.ok().body(CartItemResponse.from(cartService.findById(cartItemId)));
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(@PathVariable final Long cartItemId, @RequestParam final int quantity,
                                               @AuthenticationPrincipal Long customerId) {
        cartService.updateQuantity(cartItemId, customerId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final Long cartItemId,
                                               @AuthenticationPrincipal Long customerId) {
        cartService.deleteById(customerId, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
