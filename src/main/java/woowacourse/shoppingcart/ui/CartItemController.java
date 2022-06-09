package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
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
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartsResponse;
import woowacourse.shoppingcart.dto.CartItemUpdateRequest;

@RestController
public class CartItemController {

    private final CartService cartService;

    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/users/me/carts")
    public ResponseEntity<Void> addProduct(@AuthenticationPrincipal String email,
                                           @RequestBody CartItemRequest request) {
        cartService.addProduct(email, request.getProductId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/me/carts")
    public ResponseEntity<CartsResponse> getCartProducts(@AuthenticationPrincipal String email) {
        List<CartItem> cartItems = cartService.findCartsByEmail(email);
        List<CartResponse> carts = cartItems.stream()
            .map(CartResponse::new)
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
    public ResponseEntity<CartResponse> updateProduct(@AuthenticationPrincipal String email,
                                              @PathVariable Long productId,
                                              @Valid @RequestBody CartItemUpdateRequest request) {
        cartService.updateProduct(email, productId, request.getQuantity());
        CartItem cartItem = cartService.findCartByEmailAndProductId(email, productId);
        return ResponseEntity.ok().body(new CartResponse(cartItem));
    }
}
