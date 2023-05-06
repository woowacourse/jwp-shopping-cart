package cart.cart.api;

import cart.cart.domain.CartId;
import cart.cart.service.CartService;
import cart.common.auth.annotation.BasicAuth;
import cart.common.auth.request.AuthMember;
import cart.product.domain.ProductId;
import cart.product.service.response.ProductResponse;
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
