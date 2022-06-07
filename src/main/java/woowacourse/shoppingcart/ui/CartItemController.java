package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.application.UserService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.application.CartService;

import java.net.URI;
import java.util.List;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.user.Customer;
import woowacourse.shoppingcart.dto.ProductIdRequest;

@RestController
@RequestMapping("/users/me/carts")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable final String customerName) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerName(customerName));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal String email,
                                            @RequestBody ProductIdRequest productIdRequest) {
        Long productId = Long.valueOf(productIdRequest.getProductId());
        cartService.addCart(productId, email);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final String customerName,
                                               @PathVariable final Long cartId) {
        cartService.deleteCart(customerName, cartId);
        return ResponseEntity.noContent().build();
    }
}
