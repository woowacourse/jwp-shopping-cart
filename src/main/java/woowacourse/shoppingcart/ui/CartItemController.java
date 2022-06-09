package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.request.CartItemQuantityRequest;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.request.Request;
import woowacourse.shoppingcart.dto.response.CartResponse;

@RestController
@RequestMapping("/api/customers/me/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCartItems(@AuthenticationPrincipal final LoginCustomer loginCustomer) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(loginCustomer.getId()));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(
            @Validated(Request.id.class) @RequestBody final ProductRequest productRequest,
            @AuthenticationPrincipal final LoginCustomer loginCustomer) {
        final Long cartId = cartService.addCart(productRequest.getId(), loginCustomer.getId());
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<Void> updateCartItemQuantity(@AuthenticationPrincipal final LoginCustomer loginCustomer,
                                                       @PathVariable final Long cartId,
                                                       @RequestBody final CartItemQuantityRequest cartItemQuantityRequest) {
        cartService.updateQuantity(cartId, cartItemQuantityRequest.getQuantity(), loginCustomer.getId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@AuthenticationPrincipal final LoginCustomer loginCustomer,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(loginCustomer.getId(), cartId);
        return ResponseEntity.noContent().build();
    }
}
