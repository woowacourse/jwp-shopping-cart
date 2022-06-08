package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;

@RestController
@RequestMapping("/api")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/customer/carts")
    public ResponseEntity<List<Cart>> getCartItems(@AuthenticationPrincipal final Long id) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(id));
    }

    @PostMapping("/customer/carts")
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal final Long id,
                                            @Valid @RequestBody final AddCartItemRequest request) {
        final Long cartId = cartService.addCart(id, request.getProductId());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/customer/carts")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final Long customerId,
                                               @Valid @RequestBody DeleteCartItemRequest request) {
        cartService.deleteCart(customerId, request.getCartIds());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/customer/carts")
    public ResponseEntity<Void> updateCartItem(@AuthenticationPrincipal final Long customerId,
                                               @Valid @RequestBody UpdateCartItemRequest request) {
        cartService.update(customerId, request.getProductId(), request.getQuantity());
        return ResponseEntity.noContent().build();
    }
}
