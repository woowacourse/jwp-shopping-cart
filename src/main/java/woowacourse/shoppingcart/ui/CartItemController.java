package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
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
import woowacourse.auth.config.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartQuantityRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.dto.Request;

@RestController
@RequestMapping("/customers/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal final LoginCustomer loginCustomer) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(loginCustomer.getUsername()));
    }

    @PostMapping
    public ResponseEntity<CartResponse> addCartItem(@Validated(Request.id.class) @RequestBody final Product product,
            @AuthenticationPrincipal final LoginCustomer loginCustomer) {
        final Long cartId = cartService.addCart(product.getId(), loginCustomer.getUsername());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final LoginCustomer loginCustomer,
            @PathVariable final Long cartId) {
        cartService.deleteCart(loginCustomer.getUsername(), cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllCartItem(@AuthenticationPrincipal final LoginCustomer loginCustomer) {
        cartService.deleteAllCart(loginCustomer.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<Void> updateCartItem(@AuthenticationPrincipal final LoginCustomer loginCustomer, @PathVariable final Long cartId, @RequestBody final CartQuantityRequest cartQuantityRequest) {
        cartService.updateCart(loginCustomer.getUsername(), cartId, cartQuantityRequest.getQuantity());
        return ResponseEntity.ok().build();
    }
}
