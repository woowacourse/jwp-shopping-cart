package cart.controller;

import cart.dto.AuthInfo;
import cart.dto.ProductResponseDto;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.service.AuthService;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public List<ProductResponseDto> getCartProduct(HttpServletRequest request) {
        return cartService.getCartItems(getUserIdByAuth(request));
    }

    @PostMapping("/cart/{productId}")
    public ResponseEntity addProductToCart(@PathVariable int productId, HttpServletRequest request) {
        cartService.addCartItem(getUserIdByAuth(request), productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity deleteProductInCart(@PathVariable int productId, HttpServletRequest request) {
        cartService.deleteCartItem(getUserIdByAuth(request), productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private int getUserIdByAuth(final HttpServletRequest request) {
        AuthInfo authInfo = new BasicAuthorizationExtractor().extract(request);
        return authService.findUserIdByAuthInfo(authInfo);
    }
}
