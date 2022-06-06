package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartItemRequest;
import woowacourse.shoppingcart.dto.Request;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerName}/carts")
public class CartItemController {

    private final CartService cartService;
    private final AuthService authService;

    public CartItemController(final CartService cartService, final AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable final String customerName) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customerName));
    }

    @PatchMapping
    public ResponseEntity<Void> updateCartItem(HttpServletRequest request,
                                               @RequestBody final CartItemRequest cartItemRequest) {
        cartService.updateCartItem(cartItemRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final Product product,
                                            @PathVariable final String customerName) {
        final Long cartId = cartService.addCart(product.getId(), customerName);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }

    private String getNameFromToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        return authService.getNameFromToken(token);
    }
}
