package cart.controller;

import cart.auth.Auth;
import cart.auth.Credentials;
import cart.dto.response.CartResponse;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<CartResponse>> cartList(@Auth Credentials credentials) {
        List<CartResponse> response = cartService.findAllByEmailWithPassword(credentials);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Void> cartAdd(@Auth Credentials credentials, @PathVariable int productId) {
        int cartId = cartService.save(credentials, productId);

        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> cartRemove(@PathVariable int id) {
        cartService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
