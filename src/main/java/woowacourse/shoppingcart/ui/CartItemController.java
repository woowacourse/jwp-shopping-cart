package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.ui.LoginCustomer;
import woowacourse.shoppingcart.application.dto.CartItemResponse;
import woowacourse.shoppingcart.application.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> showCartItems(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        return ResponseEntity.ok().body(cartService.findAllByCustomerId(loginCustomer.getId()));
    }

    @PostMapping("/products/{id}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                            @PathVariable Long id) {
        cartService.saveCartItem(id, loginCustomer.getId());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                         @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }
}
