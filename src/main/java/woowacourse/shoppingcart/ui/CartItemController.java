package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.cartitem.CartItem;
import woowacourse.shoppingcart.dto.cartItem.CartItemAddRequest;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable final String customerName) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customerName));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Valid @RequestBody final CartItemAddRequest request,
                                            @AuthenticationPrincipal String username) {
        final Long cartItemId = cartService.add(username, request);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartItemId}")
                .buildAndExpand(cartItemId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }
}
