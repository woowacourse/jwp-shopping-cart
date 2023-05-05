package cart.controller;

import cart.auth.Auth;
import cart.auth.Credential;
import cart.service.CartService;
import cart.service.dto.CartAllProductSearchResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carts/products")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartAllProductSearchResponse>> searchAll(
            @Auth Credential credential
    ) {
        return ResponseEntity
                .ok()
                .body(cartService.searchAllCartProducts(credential));
    }
}
