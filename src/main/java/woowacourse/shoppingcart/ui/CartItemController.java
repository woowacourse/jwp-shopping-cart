package woowacourse.shoppingcart.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartItemResponses;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.ProductExistenceResponse;

@RestController
@RequestMapping("/api/customers/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final int customerId,
                                            @RequestBody final CartRequest cartRequest) {
        final Long cartId = cartService.addCart(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @GetMapping
    public ResponseEntity<CartItemResponses> getCartItems(@AuthenticationPrincipal final int customerId) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(customerId));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductExistenceResponse> hasProduct(@AuthenticationPrincipal final int customerId,
                                                               @PathVariable Long productId) {
        final boolean result = cartService.hasProduct(customerId, productId);
        return ResponseEntity.ok().body(new ProductExistenceResponse(result));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final int customerId,
                                               @PathVariable final Long cartItemId) {
        cartService.deleteCart(cartItemId, customerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(@AuthenticationPrincipal final int customerId,
                                               @PathVariable final Long cartItemId,
                                               @RequestBody CartRequest cartRequest) {

        cartService.updateCartItem(
                cartItemId, customerId,
                cartRequest.getProductId(),
                cartRequest.getQuantity()
        );

        return ResponseEntity.noContent().build();
    }

}
