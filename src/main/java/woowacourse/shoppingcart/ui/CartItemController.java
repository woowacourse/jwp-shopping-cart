package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.ui.dto.LoginCustomer;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.application.dto.CartResponse;
import woowacourse.shoppingcart.ui.dto.ProductChangeRequest;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> showCarts(@AuthenticationPrincipal @Valid final LoginCustomer loginCustomer) {
        List<CartResponse> cartResponses = cartService.findAll(loginCustomer.getId());
        return ResponseEntity.ok(cartResponses);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<Void> saveCartItem(@AuthenticationPrincipal @Valid final LoginCustomer loginCustomer,
                                             @PathVariable final Long productId) {
        cartService.save(loginCustomer.getId(), productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteCustomer(@AuthenticationPrincipal @Valid final LoginCustomer loginCustomer,
                                               @PathVariable final Long productId) {
        cartService.delete(loginCustomer.getId(), productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<Void> updateCustomer(@AuthenticationPrincipal @Valid final LoginCustomer loginCustomer,
                                               @PathVariable final Long productId,
                                               @RequestBody @Valid ProductChangeRequest productChangeRequest) {
        cartService.updateQuantity(loginCustomer.getId(), productId, productChangeRequest.getQuantity());
        return ResponseEntity.noContent().build();
    }
}
