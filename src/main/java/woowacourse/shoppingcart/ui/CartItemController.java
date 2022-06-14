package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.domain.LoginCustomer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.QuantityUpdateRequest;
import woowacourse.shoppingcart.dto.Request;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers/me/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        return ResponseEntity.ok().body(cartService.findCarts(loginCustomer.getUserName()));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                            @Validated(Request.id.class) @RequestBody final Product product) {
        final Long cartId = cartService.addCart(loginCustomer.getUserName(), product.getId());

        return ResponseEntity.created(
                URI.create("api/customers/me/carts/"+cartId)).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(loginCustomer.getUserName(), cartId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<Void> updateQuantity(@PathVariable final Long cartId, @RequestBody QuantityUpdateRequest request) {
        cartService.updateQuantity(cartId, request.getQuantity());
        return ResponseEntity.noContent().build();
    }
}
