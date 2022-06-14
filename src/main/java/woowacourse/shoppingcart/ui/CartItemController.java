package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.ChangeCartItemQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
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
    public ResponseEntity<List<CartItemResponse>> showCartItems(
      @AuthenticationPrincipal Customer customer) {
        List<CartItemResponse> cartItemResponses = cartService.showCartItems(customer);
        return ResponseEntity.ok().body(cartItemResponses);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Customer customer,
      @PathVariable Long productId) {
        cartService.addCart(customer, productId);
        return ResponseEntity.created(URI.create("/api/carts/products/" + productId)).build();
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Customer customer, @PathVariable Long productId) {
        cartService.deleteCart(customer, productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<Void> changeCartItemQuantity(
      @AuthenticationPrincipal Customer customer, @PathVariable Long productId,
      @RequestBody ChangeCartItemQuantityRequest request) {
        cartService.changeQuantity(customer, request, productId);
        return ResponseEntity.noContent().build();
    }
}
