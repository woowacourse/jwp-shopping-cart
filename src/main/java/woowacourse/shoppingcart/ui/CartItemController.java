package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;

import java.net.URI;

@RestController
@RequestMapping("/customers/cart")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public CartResponse getCartItems(@AuthenticationPrincipal long customerId) {
        return cartService.findCartByCustomerId(customerId);
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal long customerId, @RequestBody CartRequest cartRequest) {
        final Long cartId = cartService.addCart(cartRequest.getProductId(), customerId);
        final URI responseLocation = UriCreator.withCurrentPath(String.format("/%d", cartId));

        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final long customerId,
                                               @RequestBody CartRequest cartRequest) {
        cartService.deleteCart(customerId, cartRequest.getProductId());
        return ResponseEntity.noContent().build();
    }
}
