package woowacourse.shoppingcart.ui;

import java.util.List;
import javax.validation.Valid;
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
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;
import woowacourse.shoppingcart.dto.LoginCustomer;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> add(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                                @RequestBody @Valid CartItemCreateRequest request) {
        final CartItemResponse response = cartService.add(loginCustomer, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(loginCustomer));
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateQuantity(@AuthenticationPrincipal LoginCustomer loginCustomer,
                                                         @PathVariable Long cartItemId,
                                                         @RequestBody @Valid CartItemUpdateRequest request) {
        return ResponseEntity.ok(cartService.updateQuantity(loginCustomer, cartItemId, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll(@AuthenticationPrincipal LoginCustomer loginCustomer) {
        cartService.deleteAll(loginCustomer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final Long cartItemId,
                                               @AuthenticationPrincipal LoginCustomer loginCustomer) {
        cartService.deleteCart(loginCustomer, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
