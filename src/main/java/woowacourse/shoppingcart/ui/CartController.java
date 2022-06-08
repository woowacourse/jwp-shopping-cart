package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.CartProductRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartsResponse;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/users/me/carts")
    public ResponseEntity<Void> addProduct(@AuthenticationPrincipal String email,
                                           @RequestBody CartProductRequest request) {
        cartService.addProduct(email, request.getProductId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/me/carts")
    public ResponseEntity<CartsResponse> getCartProducts(@AuthenticationPrincipal String email) {
        List<CartItem> cartItems = cartService.findCartsByEmail(email);
        List<CartResponse> carts = cartItems.stream()
            .map(cartItem -> new CartResponse(cartItem.getProduct(), cartItem.getQuantity()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(new CartsResponse(carts));
    }

    @DeleteMapping("/users/me/carts/{productId}")
    public ResponseEntity<Void> remoteProduct(@AuthenticationPrincipal String email,
                                              @PathVariable Long productId) {
        cartService.deleteProduct(email, productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/me/carts/{productId}")
    public ResponseEntity<Void> updateProduct(@AuthenticationPrincipal String email,
                                              @PathVariable Long productId,
                                              @RequestBody UpdateCartItemRequest request) {
        cartService.updateProduct(email, productId, request.getQuantity());
        return ResponseEntity.noContent().build();
    }
}
