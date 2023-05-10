package cart.controller;

import cart.controller.annotaion.Authentication;
import cart.controller.dto.request.LoginRequest;
import cart.controller.dto.response.CartItemResponse;
import cart.service.AuthService;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class CartController {

    private final AuthService authService;
    private final CartService cartService;

    public CartController(AuthService authService, CartService cartService) {
        this.authService = authService;
        this.cartService = cartService;
    }

    @PostMapping("/carts/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCartItem(@Authentication LoginRequest loginRequest, @PathVariable(value = "productId") Long productId) {
        cartService.addCart(authService.loadUserByEmailAndPassword(loginRequest).getId(), productId);
    }

    @GetMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    public String showCart() {
        return "cart";
    }

    @GetMapping("/carts")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<CartItemResponse>> showCart(@Authentication LoginRequest loginRequest) {
        List<CartItemResponse> cartItemsByUser = cartService.findCartItems(authService.loadUserByEmailAndPassword(loginRequest).getId());
        return ResponseEntity.ok(cartItemsByUser);
    }

    @DeleteMapping("/carts/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@Authentication LoginRequest loginRequest, @PathVariable(value = "cartId") Long cartId) {
        cartService.deleteCartItem(authService.loadUserByEmailAndPassword(loginRequest).getId(), cartId);
    }
}
