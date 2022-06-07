package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.cartitem.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemRequest;
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
    public ResponseEntity<List<CartItem>> getCartItems(@AuthenticationPrincipal final String userName) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(userName));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final Product product,
                                            @AuthenticationPrincipal final String userName) {
        final Long cartId = cartService.addCart(product.getId(), userName);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final String userName,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(userName, cartId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<Void> updateQuantity(@AuthenticationPrincipal final String userName,
                                               @PathVariable final Long cartId,
                                               @RequestBody final CartItemRequest cartItemRequest) {
        cartService.updateQuantity(userName, cartId, cartItemRequest);
        return ResponseEntity.ok().build();
    }
}
