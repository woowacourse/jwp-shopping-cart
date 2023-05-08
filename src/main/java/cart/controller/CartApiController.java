package cart.controller;

import cart.dto.AuthInfo;
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
    public List<ProductResponseDto> getCartProduct(@AuthPrincipal AuthInfo authInfo) {
        return cartService.getCartItems(getUserIdByAuth(authInfo));
    }

    @PostMapping("/cart/{productId}")
    public ResponseEntity addProductToCart(@PathVariable int productId, @AuthPrincipal AuthInfo authInfo) {
        cartService.addCartItem(getUserIdByAuth(authInfo), productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity deleteProductInCart(@PathVariable int productId, @AuthPrincipal AuthInfo authInfo) {
        cartService.deleteCartItem(getUserIdByAuth(authInfo), productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private int getUserIdByAuth(final AuthInfo authInfo) {
        return authService.findUserIdByAuthInfo(authInfo);
    }
}
