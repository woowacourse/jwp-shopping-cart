package cart.controller;

import cart.auth.Auth;
import cart.auth.Credential;
import cart.controller.dto.AddCartItemRequest;
import cart.controller.dto.CartResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<CartResponse>> showCartProducts(@Auth Credential credential) {
        final Long memberId = credential.getMemberId();
        final List<CartResponse> products = cartService.findCartProducts(memberId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public ResponseEntity<String> addCartProducts(@Auth Credential credential,
                                                  @RequestBody AddCartItemRequest addCartItemRequest) {
        final Long cartId = cartService.addCartProducts(credential, addCartItemRequest);
        return ResponseEntity.created(URI.create("/carts/products/" + cartId)).build();
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteCartProducts(@PathVariable Long cartId) {
        cartService.deleteProducts(cartId);
        return ResponseEntity.noContent().build();
    }
}
