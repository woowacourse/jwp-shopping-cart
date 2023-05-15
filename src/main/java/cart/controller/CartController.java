package cart.controller;

import cart.auth.BasicAuth;
import cart.auth.Credentials;
import cart.dto.response.CartResponse;
import cart.service.CartService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<CartResponse>> cartList(@BasicAuth Credentials credentials) {
        List<CartResponse> response = cartService.findAllByEmailWithPassword(credentials);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/carts/{productId}")
    public ResponseEntity<Map<String, Integer>> cartAdd(@BasicAuth Credentials credentials,
                                                        @PathVariable int productId) {
        int cartId = cartService.save(credentials, productId);

        Map<String, Integer> response = new HashMap<>();
        response.put("id", cartId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Map<String, String>> cartRemove(@PathVariable int id) {
        cartService.delete(id);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
