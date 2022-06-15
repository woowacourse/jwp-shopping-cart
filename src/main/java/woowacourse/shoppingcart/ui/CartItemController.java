package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.request.CartAddRequest;
import woowacourse.shoppingcart.dto.request.CartDeleteRequest;
import woowacourse.shoppingcart.dto.request.CartUpdateRequest;
import woowacourse.shoppingcart.dto.response.CartResponse;

@RestController
@RequestMapping("/api/customer/carts")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Long customerId,
                                            @Valid @RequestBody final CartAddRequest cartAddRequest) {
        final Long cartId = cartService.addCart(customerId, cartAddRequest);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal Long customerId) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(customerId));
    }

    @PutMapping
    public ResponseEntity<Void> updateCartItem(@AuthenticationPrincipal Long customerId,
                                               @Valid @RequestBody final CartUpdateRequest cartUpdateRequest) {
        cartService.updateCart(customerId, cartUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Long customerId,
                                               @Valid @RequestBody final CartDeleteRequest cartDeleteRequest) {
        cartService.deleteCart(customerId, cartDeleteRequest);
        return ResponseEntity.noContent().build();
    }
}
