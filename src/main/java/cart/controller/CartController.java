package cart.controller;

import cart.controller.argumentresolver.BasicAuthorization;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ItemResponse;
import cart.controller.dto.LoginUser;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<Void> createCart(@BasicAuthorization final LoginUser loginUser, @PathVariable final Long itemId) {
        cartService.saveCart(loginUser.getEmail(), itemId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/"))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> readCart(@BasicAuthorization LoginUser loginUser) {
        List<CartResponse> carts = cartService.loadItemInsideCart(loginUser.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(carts);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable final Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.status(HttpStatus.OK)
                .location(URI.create("/"))
                .build();
    }
}
