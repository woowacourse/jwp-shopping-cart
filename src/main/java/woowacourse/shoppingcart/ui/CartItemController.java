package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;

@RestController
@RequestMapping("/api/members/me/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartItems(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity.ok().body(cartService.findCartsByMemberId(memberId));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(
            @Validated(Request.id.class) @RequestBody final AddCartItemRequest addCartItemRequest,
            @AuthenticationPrincipal Long memberId) {
        final Long cartId = cartService.addCart(addCartItemRequest.getProductId(), memberId);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable final Long cartId,
                                                       @RequestBody final UpdateQuantityRequest updateQuantityRequest) {
        cartService.updateCartItemQuantity(cartId, updateQuantityRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Long memberId,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(memberId, cartId);
        return ResponseEntity.noContent().build();
    }
}
