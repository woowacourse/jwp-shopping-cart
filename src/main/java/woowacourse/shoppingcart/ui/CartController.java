package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
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
import woowacourse.shoppingcart.dto.CartQuantityUpdateRequest;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;

@RestController
@RequestMapping("/api/members/me/carts")
public class CartController {
    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity.ok(cartService.findCartsById(memberId));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final Long memberId,
                                            @RequestBody @Valid final CartRequest cartRequest) {
        final Long id = cartService.addCart(memberId, cartRequest);
        return ResponseEntity.created(URI.create("/api/members/me/carts/" + id)).build();
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Void> updateCartQuantity(@AuthenticationPrincipal final Long memberId,
                                                   @PathVariable final Long cartId,
                                                   @RequestBody @Valid final CartQuantityUpdateRequest cartQuantityUpdateRequest) {
        cartService.updateCartQuantity(memberId, cartId, cartQuantityUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final Long memberId,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(memberId, cartId);
        return ResponseEntity.noContent().build();
    }
}
