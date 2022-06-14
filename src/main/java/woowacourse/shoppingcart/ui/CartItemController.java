package woowacourse.shoppingcart.ui;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.request.CartRequest;
import woowacourse.shoppingcart.dto.request.DeleteProductRequest;
import woowacourse.shoppingcart.dto.request.UpdateCartRequests;
import woowacourse.shoppingcart.dto.response.CartResponse;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal String usernameByToken,
                                            @RequestBody @Valid CartRequest cartRequest) {
        cartService.addCart(usernameByToken, cartRequest);
        URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCartItems(@AuthenticationPrincipal String usernameByToken) {
        return ResponseEntity.ok().body(cartService.findCartByUsername(usernameByToken));
    }

    @PatchMapping
    public ResponseEntity<CartResponse> updateCartItems(@AuthenticationPrincipal String usernameByToken,
                                                        @RequestBody @Valid UpdateCartRequests updateCartRequests) {
        return ResponseEntity.ok(cartService.updateCartItems(usernameByToken, updateCartRequests));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal String usernameByToken,
                                               @RequestBody @Valid DeleteProductRequest deleteProductRequest) {
        cartService.deleteCartItem(usernameByToken, deleteProductRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCartItem(@AuthenticationPrincipal String usernameByToken) {
        cartService.deleteAllCartItem(usernameByToken);
        return ResponseEntity.noContent().build();
    }
}
