package cart.controller;

import cart.controller.dto.request.LoginRequest;
import cart.service.AuthService;
import cart.service.CartService;
import cart.util.BasicAuthExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CartController {

    private final AuthService authService;
    private final CartService cartService;
    private final BasicAuthExtractor basicAuthExtractor = new BasicAuthExtractor();

    public CartController(AuthService authService, CartService cartService) {
        this.authService = authService;
        this.cartService = cartService;
    }

    @PostMapping("/addCart/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCartItem(HttpServletRequest request, @PathVariable(value = "productId") Long productId) {
        LoginRequest loginRequest = basicAuthExtractor.extract(request);
        cartService.addCart(authService.basicLogin(loginRequest), productId);
    }

    @DeleteMapping("/deleteCart")
    public void deleteCartItem(HttpServletRequest request) {

    }
}
