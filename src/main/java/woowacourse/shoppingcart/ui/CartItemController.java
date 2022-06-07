package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemChangeQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.application.CartService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> showCartItems(
      @AuthenticationPrincipal LoginCustomer loginCustomer) {
        List<CartItemResponse> cartItemResponses = cartService.showCartItems(loginCustomer);
        return ResponseEntity.ok().body(cartItemResponses);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal LoginCustomer loginCustomer,
      @PathVariable Long productId) {
        cartService.addCart(loginCustomer, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal LoginCustomer loginCustomer, @PathVariable Long productId) {
        cartService.deleteCart(loginCustomer, productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<Void> changeCartItemQuantity(
      @AuthenticationPrincipal LoginCustomer loginCustomer, @PathVariable Long productId,
      @RequestBody CartItemChangeQuantityRequest request) {
        cartService.changeQuantity(loginCustomer, request, productId);
        return ResponseEntity.noContent().build();
    }
}
