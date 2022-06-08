package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartAdditionRequest;

@RestController
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/api/carts")
    public ResponseEntity<List<Cart>> getCartItems(@AuthenticationPrincipal final String email) {
        return ResponseEntity.ok().body(cartService.findCartsByEmail(email));
    }

    @PostMapping("/api/carts/products")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final String email,
                                            @RequestBody final CartAdditionRequest cartAdditionRequest) {
        cartService.addCart(cartAdditionRequest, email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/api/customers/{customerName}/carts/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }
}
