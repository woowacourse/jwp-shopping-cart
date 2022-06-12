package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.application.dto.UpdateQuantityServiceRequest;
import woowacourse.shoppingcart.ui.dto.AddCartRequest;
import woowacourse.shoppingcart.ui.dto.CartResponse;
import woowacourse.shoppingcart.ui.dto.UpdateQuantityRequest;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/members/me/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Long memberId, @RequestBody AddCartRequest request) {
        final Long cartId = cartService.add(memberId, request.getProductId());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity.ok().body(cartService.findCarts(memberId));
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Void> updateQuantity(@AuthenticationPrincipal Long memberId,
                                               @PathVariable Long cartId,
                                               @RequestBody UpdateQuantityRequest request) {

        cartService.updateQuantity(new UpdateQuantityServiceRequest(memberId, cartId, request.getQuantity()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Long memberId,
                                               @PathVariable Long cartId) {
        cartService.deleteCart(memberId, cartId);
        return ResponseEntity.noContent().build();
    }
}
