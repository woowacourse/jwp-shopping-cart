package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
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
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartRequest;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(@AuthenticationPrincipal Long id) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(id));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Long id, @RequestBody CartRequest cartRequest) {
        final Long cartId = cartService.addCart(id, cartRequest.getId(), cartRequest.getQuantity());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Long id, @RequestBody List<Long> productIds) {
        cartService.deleteCart(id, productIds);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateCartItem(@AuthenticationPrincipal Long id, @RequestBody CartRequest cartRequest) {
        cartService.updateCart(id, cartRequest.getId(), cartRequest.getQuantity());
        return ResponseEntity.ok().build();
    }
}
