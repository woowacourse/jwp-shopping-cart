package cart.controller;

import cart.authentication.Auth;
import cart.dto.CartDto;
import cart.service.CartService;
import cart.vo.Email;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts/{id}")
    public ResponseEntity<Void> addProductToCart(@PathVariable("id") Long productId, Auth auth) {
        CartDto cartDto = new CartDto(Email.from(auth.getEmail()), productId);
        cartService.save(cartDto);
        return ResponseEntity.created(URI.create("carts"))
                .build();
    }
    
}
