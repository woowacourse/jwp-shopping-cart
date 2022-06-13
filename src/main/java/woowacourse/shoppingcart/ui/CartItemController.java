package woowacourse.shoppingcart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal String userNameByToken, @Valid @RequestBody CartProductRequest cartProductRequest) {
        cartService.addCart(cartProductRequest, userNameByToken);
        return ResponseEntity.created(URI.create("/cart")).build();
    }

    @GetMapping
    public ResponseEntity<CartItemsResponse> findCartItems(@AuthenticationPrincipal String userNameByToken) {
        return ResponseEntity.ok().body(cartService.findCart(userNameByToken));
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(@AuthenticationPrincipal String userNameByToken) {
        cartService.deleteAll(userNameByToken);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal String userNameByToken, @RequestBody DeleteCartItemRequest deleteCartItemRequest) {
        cartService.deleteCart(userNameByToken, deleteCartItemRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<CartItemsResponse> updateCartItem(@AuthenticationPrincipal String userNameByToken, @RequestBody UpdateCartItemsRequest updateCartItemsRequest) {
        return ResponseEntity.ok().body(cartService.updateCartItems(userNameByToken, updateCartItemsRequest));
    }
}
