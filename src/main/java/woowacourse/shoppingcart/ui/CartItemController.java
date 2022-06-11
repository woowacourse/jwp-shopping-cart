package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.cart.CartAdditionRequest;
import woowacourse.shoppingcart.dto.cart.CartResponse;
import woowacourse.shoppingcart.dto.cart.CartUpdateRequest;

@RestController
@RequestMapping("/api/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(@AuthenticationPrincipal final Email email) {
        return ResponseEntity.ok().body(cartService.findCartsByEmail(email));
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final Email email,
                                            @RequestBody final CartAdditionRequest cartAdditionRequest) {
        cartService.addCartItem(email, cartAdditionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/products")
    public ResponseEntity<List<CartResponse>> updateCartItem(@AuthenticationPrincipal final Email email,
                                                             @RequestBody final CartUpdateRequest cartUpdateRequest) {
        cartService.updateCartItem(email, cartUpdateRequest);
        List<CartResponse> responses = cartService.findCartsByEmail(email).stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/products")
    public ResponseEntity<List<CartResponse>> deleteCartItem(@AuthenticationPrincipal final Email email,
                                                             @RequestParam final Long productId) {
        cartService.deleteCartItem(email, productId);
        List<CartResponse> responses = cartService.findCartsByEmail(email).stream()
                .map(CartResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responses);
    }
}
