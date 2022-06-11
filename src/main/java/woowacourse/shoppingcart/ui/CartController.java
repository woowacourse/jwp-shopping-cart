package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.Login;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.cart.CartDeleteRequest;
import woowacourse.shoppingcart.dto.cart.CartProduct;
import woowacourse.shoppingcart.dto.cart.CartSetRequest;
import woowacourse.shoppingcart.dto.cart.CartSetResponse;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartProduct>> getCartItems(@Login String email) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerEmail(email));
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<CartProduct> setCart(@Login String email, @RequestBody CartSetRequest cartSetRequest,
                                               @PathVariable Long productId) {
        CartSetResponse response = cartService.addCart(cartSetRequest, email, productId);
        if (response.isCreated()) {
            return ResponseEntity.created(createUri(response.getId()))
                    .body(new CartProduct(response.getProductId(), response.getImage(), response.getName(),
                            response.getPrice(), response.getPrice()));
        }
        return ResponseEntity.ok(new CartProduct(response.getProductId(), response.getImage(), response.getName(),
                response.getPrice(), response.getPrice()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItem(@Login String email, @RequestBody CartDeleteRequest cartDeleteRequest) {
        cartService.deleteCart(email, cartDeleteRequest);
        return ResponseEntity.noContent().build();
    }

    private URI createUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/cart/products")
                .path("/" + id)
                .build().toUri();
    }
}
