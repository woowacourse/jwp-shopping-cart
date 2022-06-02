package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(HttpServletRequest request) {
        return ResponseEntity.ok().body(cartService.findCarts(request));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(HttpServletRequest request,
                                            @Validated(Request.id.class) @RequestBody final Product product) {
        final Long cartId = cartService.addCart(request, product.getId());
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
        cartService.deleteCart(request, cartId);
        return ResponseEntity.noContent().build();
    }
}
