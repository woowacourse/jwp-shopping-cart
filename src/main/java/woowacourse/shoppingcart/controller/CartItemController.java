package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.controller.CustomerId;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.service.CartService;

@RestController
@RequestMapping("/api/customers/cart")
public class CartItemController {
    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getCartItems(@CustomerId final Long customerId) {
        return ResponseEntity.ok().body(cartService.findCartsByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@Validated(Request.id.class) @RequestBody final Product product,
                                            @CustomerId final Long customerId) {
        final Long cartId = cartService.addCart(product.getId(), customerId);
        final URI responseLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cartId}")
                .buildAndExpand(cartId)
                .toUri();
        return ResponseEntity.created(responseLocation).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@CustomerId final Long customerId, @PathVariable final Long cartId) {
        cartService.deleteCart(customerId, cartId);
        return ResponseEntity.noContent().build();
    }
}
