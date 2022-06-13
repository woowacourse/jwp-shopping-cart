package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.ui.LoginCustomer;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.application.dto.CartItemResponse;
import woowacourse.shoppingcart.ui.dto.CartItemQuantityRequest;

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

    @PatchMapping("/products/{id}")
    public ResponseEntity<Void> itemQuantityUpdate(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                                   @PathVariable Long id,
                                                   @RequestBody CartItemQuantityRequest cartItemQuantityRequest) {
        cartService.updateQuantity(
                cartItemQuantityRequest.toServiceRequest(
                        loginCustomer.getId(),
                        id,
                        cartItemQuantityRequest.getQuantity()));

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                               @PathVariable Long id) {
        cartService.deleteCartItem(loginCustomer.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
