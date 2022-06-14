package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartDeleteRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartSaveRequest;
import woowacourse.shoppingcart.dto.CartUpdateRequest;
import javax.validation.Valid;
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
                                            @RequestBody @Valid final CartSaveRequest cartSaveRequest) {
        final Long cartId = cartService.add(customerId, cartSaveRequest);
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

    @PutMapping
    public ResponseEntity<Void> update(@AuthenticationPrincipal final Long id,
                                       @RequestBody @Valid final CartUpdateRequest request) {
        cartService.updateQuantity(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final Long id,
                                               @RequestBody final CartDeleteRequest request) {
        cartService.delete(id, request);

        return ResponseEntity.noContent().build();
    }
}
