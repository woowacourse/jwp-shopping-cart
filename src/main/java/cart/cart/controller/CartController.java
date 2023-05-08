package cart.cart.controller;

import cart.cart.dto.CartResponse;
import cart.cart.dto.ExceptionResponse;
import cart.cart.resolver.Authorization;
import cart.cart.service.CartService;
import cart.cart.exception.AuthorizationException;
import cart.cart.dto.AuthInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String showCart() {
        return "cart";
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<CartResponse>> showCarts(@Authorization AuthInfo authInfo) {
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        List<CartResponse> cartResponses = cartService.showCart(email, password);
        return ResponseEntity.ok().body(cartResponses);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCart(@RequestParam("productId") Long productId, @Authorization AuthInfo authInfo) {
        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        cartService.addCart(productId, email, password);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCart(@PathVariable("id") Long cartId, @Authorization AuthInfo authInfo) {

        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        cartService.deleteCartById(cartId, email, password);
        return "cart";
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthorizationException(AuthorizationException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(exception.getMessage()));
    }
}
