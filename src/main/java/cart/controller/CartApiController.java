package cart.controller;

import cart.dto.AuthRequest;
import cart.dto.CartItem;
import cart.dto.ProductResponseDto;
import cart.service.AuthService;
import cart.service.CartService;
import cart.auth.AuthPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartApiController {

    private final CartService cartService;
    private final AuthService authService;

    public CartApiController(CartService cartService, AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }

    @GetMapping(value="/cart-products", produces="application/json")
    public List<ProductResponseDto> getCartProduct(@AuthPrincipal AuthRequest authRequest) {
        return cartService.getCartProducts(getUserIdByAuth(authRequest));
    }

    @PostMapping("/cart/{productId}")
    public ResponseEntity addProductToCart(@PathVariable int productId, @AuthPrincipal AuthRequest authRequest) {
        cartService.addCartItem(new CartItem(getUserIdByAuth(authRequest), productId));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity deleteProductInCart(@PathVariable int productId, @AuthPrincipal AuthRequest authRequest) {
        cartService.deleteCartItem(new CartItem(getUserIdByAuth(authRequest), productId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private int getUserIdByAuth(final AuthRequest authRequest) {
        return authService.findUserIdByAuth(authRequest);
    }
}
