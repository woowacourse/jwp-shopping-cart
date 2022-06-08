package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartAdditionRequest;

@RestController
@RequestMapping("/api/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(@AuthenticationPrincipal final String email) {
        return ResponseEntity.ok().body(cartService.findCartsByEmail(email));
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final String email,
                                            @RequestBody final CartAdditionRequest cartAdditionRequest) {
        cartService.addCartItem(cartAdditionRequest, email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<List<Cart>> deleteCartItem(@AuthenticationPrincipal final String email,
                                               @RequestParam final Long productId) {
        cartService.deleteCartItem(email, productId);
        return ResponseEntity.ok().body(cartService.findCartsByEmail(email));
    }
}
