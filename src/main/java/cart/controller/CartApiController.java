package cart.controller;

import cart.auth.AuthMember;
import cart.auth.AuthenticationPrincipal;
import cart.controller.dto.response.CartResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartApiController {

    private final CartService cartService;

    public CartApiController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> getCarts(@AuthenticationPrincipal AuthMember member) {
        return ResponseEntity.ok().body(cartService.findAll(member));
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Void> addCart(@PathVariable int productId, @AuthenticationPrincipal AuthMember member) {
        cartService.addCart(productId, member);
        return ResponseEntity.created(URI.create("/cart")).build();
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable int cartId, @AuthenticationPrincipal AuthMember member) {
        cartService.deleteCart(cartId, member);
        return ResponseEntity.noContent().build();
    }

}
