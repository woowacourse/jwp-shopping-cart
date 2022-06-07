package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponses;
import woowacourse.shoppingcart.dto.DeleteProductRequest;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.UpdateCartRequests;

@RestController
@RequestMapping("/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal String usernameByToken,
                                            @Validated(Request.id.class) @RequestBody CartRequest cartRequest) {
        cartService.addCart(usernameByToken, cartRequest);
        URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<CartResponses> getCartItems(@AuthenticationPrincipal String usernameByToken) {
        return ResponseEntity.ok().body(cartService.findCartsByUsername(usernameByToken));
    }

    @PatchMapping
    public ResponseEntity<CartResponses> updateCartItems(@AuthenticationPrincipal String usernameByToken,
                                                         @RequestBody UpdateCartRequests updateCartRequests) {
        return ResponseEntity.ok(cartService.updateCartItems(usernameByToken, updateCartRequests));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal String usernameByToken,
                                               @RequestBody DeleteProductRequest deleteProductRequest) {
        cartService.deleteCart(deleteProductRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllCartItem(@AuthenticationPrincipal String usernameByToken) {
        cartService.deleteAllCart(usernameByToken);
        return ResponseEntity.noContent().build();
    }
}
