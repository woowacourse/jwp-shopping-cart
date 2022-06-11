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
        return ResponseEntity.ok().body(cartService.getCart(userNameByToken));
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(@AuthenticationPrincipal String userNameByToken) {
        cartService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal String userNameByToken, @RequestBody DeleteProductRequest deleteProductRequest) {
        cartService.deleteCart(deleteProductRequest);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<CartItemsResponse> modifyCartItem(@AuthenticationPrincipal String userNameByToken, @RequestBody ModifyProductRequests modifyProductRequests) {
        return ResponseEntity.ok().body(cartService.modifyCartItems(modifyProductRequests));
    }
}
