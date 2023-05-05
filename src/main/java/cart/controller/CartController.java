package cart.controller;

import cart.dto.CartProductResponse;
import cart.dto.CartRequest;
import cart.service.CartService;
import cart.ui.CartAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts/products")
    public ResponseEntity<List<CartProductResponse>> allCartProducts(@CartAuthentication String email) {
        List<CartProductResponse> carts = cartService.getCartsByEmail(email);
        return ResponseEntity.ok().body(carts);
    }

    @PostMapping("/carts/products")
    public ResponseEntity<Void> addCartProduct(@RequestBody CartRequest cartRequest, @CartAuthentication String email) {
        int cartId = cartService.addCart(cartRequest.getProductId(), email);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }

    @DeleteMapping("/carts/{cartId}/products")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable int cartId, @CartAuthentication String email) {
        cartService.deleteCart(cartId, email);
        return ResponseEntity.noContent().build();
    }
}
