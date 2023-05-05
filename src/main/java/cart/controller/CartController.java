package cart.controller;

import cart.dto.CartProductDto;
import cart.dto.CartRequestDto;
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

    @GetMapping("/carts")
    public ResponseEntity<List<CartProductDto>> allCarts(@CartAuthentication String email) {
        List<CartProductDto> carts = cartService.getCartsByEmail(email);
        return ResponseEntity.ok().body(carts);
    }

    @PostMapping("/carts")
    public ResponseEntity<Void> addCart(@RequestBody CartRequestDto cartRequestDto, @CartAuthentication String email) {
        int cartId = cartService.addCart(cartRequestDto.getProductId(), email);
        return ResponseEntity.created(URI.create("/carts/" + cartId)).build();
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable int cartId, @CartAuthentication String email) {
        cartService.deleteCart(cartId, email);
        return ResponseEntity.noContent().build();
    }
}
