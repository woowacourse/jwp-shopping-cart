package cart.controller.api;

import cart.config.auth.AuthMember;
import cart.config.auth.BasicAuth;
import cart.domain.cart.CartId;
import cart.domain.product.ProductId;
import cart.service.cart.CartService;
import cart.service.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/carts")
@RestController
public class CartApi {
    private final CartService cartService;

    public CartApi(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getCartProducts(@BasicAuth AuthMember authMember) {
        final List<ProductResponse> findProducts = cartService.findAllByMember(authMember);
        return ResponseEntity.ok(findProducts);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> addProductInCart(
            @BasicAuth AuthMember authMember,
            @PathVariable(name = "id") long productId
    ) {
        final CartId cartId = cartService.addProduct(authMember, ProductId.from(productId));
        final URI uri = URI.create("/carts/" + cartId.getId());
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProductInCart(
            @BasicAuth AuthMember authMember,
            @PathVariable(name = "id") long productId
    ) {
        cartService.removeProduct(authMember, ProductId.from(productId));
        return ResponseEntity.noContent().build();
    }
}
