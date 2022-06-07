package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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
import woowacourse.shoppingcart.dto.CartItemDeletionRequest;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;
import woowacourse.shoppingcart.service.CartItemService;

@RestController
@RequestMapping("/api/mycarts")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addCart(@AuthenticationPrincipal String email,
        @Valid @RequestBody CartItemRequest cartItemRequest) {
        CartItemResponse cartItemResponse = cartItemService.addCart(email, cartItemRequest);
        return ResponseEntity.created(URI.create("/api/mycarts/" + cartItemResponse.getId())).body(cartItemResponse);
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> findCartItems(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(cartItemService.getCartItems(email));
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> findCartItem(@AuthenticationPrincipal String email,
        @PathVariable(value = "cartItemId") long id) {
        return ResponseEntity.ok(cartItemService.getCartItem(email, id));
    }

    @PatchMapping
    public ResponseEntity<Void> updateQuantity(@AuthenticationPrincipal String email,
        @RequestBody UpdateCartItemRequest cartItemRequest) {
        cartItemService.update(email, cartItemRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal String email,
        @RequestBody CartItemDeletionRequest cartItemDeletionRequest) {
        cartItemService.delete(email, cartItemDeletionRequest);
        return ResponseEntity.noContent().build();
    }
}
