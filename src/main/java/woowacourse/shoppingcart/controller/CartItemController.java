package woowacourse.shoppingcart.controller;

import java.net.URI;
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
import woowacourse.shoppingcart.dto.CartRemovalRequest;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartsResponse;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;
import woowacourse.shoppingcart.service.CartService;

@RestController
@RequestMapping("/api/mycarts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartsResponse> getCartItems(@AuthenticationPrincipal String email) {
        final CartsResponse response = cartService.findCartsByCustomer(email);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartItems(@AuthenticationPrincipal String email,
                                                     @PathVariable Long cartId) {
        final CartResponse response = cartService.findById(email, cartId);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<CartResponse> addCartItem(
            @AuthenticationPrincipal String email, @RequestBody CartRequest request) {
        final CartResponse response = cartService.addCart(email, request);

        return ResponseEntity.created(URI.create("/pai/mycarts/" + response.getId())).body(response);
    }

    @PatchMapping
    public ResponseEntity<Void> updateQuantity(@AuthenticationPrincipal String email,
                                               @RequestBody UpdateQuantityRequest request) {
        cartService.updateQuantity(email, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal String email,
                                               @RequestBody CartRemovalRequest request) {
        cartService.deleteCart(email, request);
        return ResponseEntity.noContent().build();
    }
}
