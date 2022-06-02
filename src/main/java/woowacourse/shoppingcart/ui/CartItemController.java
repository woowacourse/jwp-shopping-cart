package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.application.CartService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers/me/carts")
public class CartItemController {
    private final CartService cartService;
    private final AuthService authService;

    public CartItemController(final CartService cartService, final AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(HttpServletRequest request) {
        String customerName = getNameFromToken(request);
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customerName));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(HttpServletRequest request,
                                            @Validated(Request.id.class) @RequestBody final Product product) {
        String customerName = getNameFromToken(request);
        final Long cartId = cartService.addCart(product.getId(), customerName);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(HttpServletRequest request,
                                         @PathVariable final Long cartId) {
        String customerName = getNameFromToken(request);
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }

    private String getNameFromToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        return authService.getNameFromToken(token);
    }
}
