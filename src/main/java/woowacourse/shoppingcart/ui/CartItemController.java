package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartAddRequest;
import woowacourse.shoppingcart.dto.CartUpdateRequest;
import woowacourse.shoppingcart.service.CartService;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(@AuthenticationPrincipal final LoginCustomer loginCustomer) {
        return ResponseEntity.ok().body(cartService.findCartsByLoginCustomer(loginCustomer));
    }

    @PostMapping
    public ResponseEntity<Cart> addCartItem(@AuthenticationPrincipal final LoginCustomer loginCustomer,
                                            @RequestBody final CartAddRequest cartAddRequest) {
        final Cart cart = cartService.addCart(loginCustomer, cartAddRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<Cart> updateCartItem(@AuthenticationPrincipal final LoginCustomer loginCustomer,
                                               @RequestBody final CartUpdateRequest request,
                                               @PathVariable final Long cartItemId) {
        final Cart cart = cartService.updateQuantity(loginCustomer, request, cartItemId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final LoginCustomer loginCustomer,
                                               @PathVariable final Long cartItemId) {
        cartService.deleteCart(loginCustomer, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
