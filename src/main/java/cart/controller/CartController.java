package cart.controller;

import cart.controller.dto.request.LoginRequest;
import cart.controller.dto.response.ProductResponse;
import cart.service.AuthService;
import cart.service.CartService;
import cart.util.BasicAuthExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
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

    @GetMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    public String showCart() {
        return "cart";
    }

    @GetMapping("/findCart")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> showCart(HttpServletRequest request) {
        System.out.println(request.getHeader("Authorization"));
        LoginRequest loginRequest = basicAuthExtractor.extract(request);
        List<ProductResponse> cartByUser = cartService.findCartByUser(authService.basicLogin(loginRequest));
        return ResponseEntity.ok(cartByUser);
    }

    @DeleteMapping("/deleteCart")
    public void deleteCartItem(HttpServletRequest request) {

    }
}
