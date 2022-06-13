package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;
import woowacourse.shoppingcart.service.CartService;

import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCartItems(@AuthenticationPrincipal String username) {
        return ResponseEntity.ok().body(cartService.findByUserName(username));
    }

    @PostMapping
    public ResponseEntity addItem(@AuthenticationPrincipal String username, @RequestBody AddCartItemRequest addCartItemRequest) {
        cartService.addItem(username, addCartItemRequest);
        return ResponseEntity.created(URI.create("/cart")).build();
    }

    @PatchMapping
    public ResponseEntity<CartResponse> updateCartItems(@AuthenticationPrincipal String username, @RequestBody UpdateCartItemRequest updateCartItemRequest) {
        return ResponseEntity.ok().body(cartService.updateItem(username, updateCartItemRequest));
    }

    @DeleteMapping
    public ResponseEntity deleteCartItems(@AuthenticationPrincipal String username, @RequestBody DeleteCartItemRequest deleteCartItemRequest) {
        cartService.deleteItem(username, deleteCartItemRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity deleteCart(@AuthenticationPrincipal String username) {
        cartService.deleteCart(username);
        return ResponseEntity.noContent().build();
    }
}
