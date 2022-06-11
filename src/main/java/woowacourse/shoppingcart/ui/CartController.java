package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductIdsRequest;
import woowacourse.shoppingcart.dto.QuantityRequest;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCartItems(@AuthenticationPrincipal Customer customer) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customer.getUsername()));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal Customer customer,
                                            @PathVariable final Long productId) {
        cartService.addCart(productId, customer.getUsername());
        return ResponseEntity.created(URI.create("/cart")).build();
    }

    @PutMapping("/{productId}/quantity")
    public ResponseEntity<Void> updateCartItemQuantity(@AuthenticationPrincipal Customer customer,
                                                       @PathVariable final Long productId,
                                                       @Valid @RequestBody QuantityRequest quantityRequest) {
        cartService.updateCartItemQuantity(quantityRequest.getQuantity(), productId, customer.getUsername());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal Customer customer,
                                               @Valid @RequestBody ProductIdsRequest productIdsRequest) {
        cartService.deleteCartItem(customer.getUsername(), productIdsRequest.getProductIds());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@AuthenticationPrincipal Customer customer) {
        cartService.deleteCart(customer.getUsername());
        return ResponseEntity.noContent().build();
    }
}
