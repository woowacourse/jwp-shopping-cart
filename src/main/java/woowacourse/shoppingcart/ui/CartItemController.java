package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customer/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final Long customerId,
                                            @RequestBody final Long productId) {
        final Long cartId = cartService.add(customerId, productId);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();

        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal final Long id) {
        final List<CartResponse> responses = cartService.findAllByCustomerId(id);

        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final Long id,
                                               @PathVariable final Long cartId) {
        cartService.delete(id, cartId);

        return ResponseEntity.noContent().build();
    }
}
