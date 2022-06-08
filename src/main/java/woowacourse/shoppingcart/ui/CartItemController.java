package woowacourse.shoppingcart.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;

@RestController
@RequestMapping("/customers/cart")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCartItems(@AuthenticationPrincipal long customerId) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal long customerId,
                                            @RequestBody CartRequest cartRequest) {
        cartService.addCart(customerId, cartRequest.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal long customerId,
                                               @RequestBody CartRequest cartRequest) {
        cartService.deleteCart(customerId, cartRequest.getProductId());
        return ResponseEntity.noContent().build();
    }
}
