package cart.controller;

import cart.auth.Auth;
import cart.auth.Credential;
import cart.controller.dto.CartResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> showCart(@Auth Credential credential) {
        final Long memberId = credential.getMemberId();
        final List<CartResponse> products = cartService.findProductsByMemberId(memberId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> addProduct(@Auth Credential credential,
                                             @PathVariable Long productId) {
        final Long memberId = credential.getMemberId();
        final Long cartId = cartService.addProduct(memberId, productId);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long cartId) {
        cartService.deleteById(cartId);
        return ResponseEntity.noContent().build();
    }
}
