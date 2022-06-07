package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartItemService;
import woowacourse.shoppingcart.dto.cartItem.CartItemDeleteRequest;
import woowacourse.shoppingcart.dto.cartItem.CartItemResponse;
import woowacourse.shoppingcart.dto.cartItem.CartItemUpdateRequest;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartItemService cartService;

    public CartItemController(final CartItemService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal Long customerId) {
        List<CartItemResponse> cartItemResponses = cartService.findCartItemsByCustomerId(customerId);
        return ResponseEntity.ok().body(cartItemResponses);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Long customerId,
                                            @PathVariable Long productId,
                                            @RequestBody CartItemUpdateRequest cartItemUpdateRequest) {
        final Long cartItemId = cartService.addCartItem(customerId, productId, cartItemUpdateRequest);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartItemId}")
                .buildAndExpand(cartItemId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Long customerId,
                                               @RequestBody CartItemDeleteRequest cartItemDeleteRequest) {
        cartService.deleteCartItemsByCustomerId(customerId, cartItemDeleteRequest);
        return ResponseEntity.noContent().build();
    }
}
