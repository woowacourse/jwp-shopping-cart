package cart.controller;

import cart.auth.Auth;
import cart.auth.Credential;
import cart.controller.dto.AddCartItemRequest;
import cart.controller.dto.CartResponse;
import cart.controller.dto.DeleteCartItemRequest;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<CartResponse>> showCartItems(@Auth Credential credential) {
        final Long memberId = credential.getMemberId();
        final List<CartResponse> products = cartService.findCartItems(memberId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    public ResponseEntity<String> addCartItem(@Auth Credential credential,
                                              @RequestBody AddCartItemRequest addCartItemRequest) {
        final Long cartId = cartService.addItem(credential, addCartItemRequest);
        return ResponseEntity.created(URI.create("/carts/products/" + cartId)).build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<String> deleteCartItem(@RequestBody DeleteCartItemRequest deleteCartItemRequest) {
        cartService.deleteItem(deleteCartItemRequest);
        return ResponseEntity.noContent().build();
    }
}
