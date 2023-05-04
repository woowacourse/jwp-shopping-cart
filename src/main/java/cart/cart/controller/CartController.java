package cart.cart.controller;

import cart.cart.dto.CartResponse;
import cart.cart.service.CartService;
import cart.global.infrastructure.AuthorizationExtractor;
import cart.member.dto.AuthInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/cart")
public class CartController {

    private final AuthorizationExtractor<AuthInfo> authorizationExtractor;
    private final CartService cartService;

    public CartController(AuthorizationExtractor<AuthInfo> authorizationExtractor, CartService cartService) {
        this.authorizationExtractor = authorizationExtractor;
        this.cartService = cartService;
    }

    @GetMapping
    public String showCart() {
        return "cart";
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<CartResponse>> showCarts(HttpServletRequest request) {
        AuthInfo authInfo = authorizationExtractor.extract(request);

        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        List<CartResponse> cartResponses = cartService.showCart(email, password);
        return ResponseEntity.ok().body(cartResponses);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCart(@RequestParam("productId") Long productId, HttpServletRequest request) {
        AuthInfo authInfo = authorizationExtractor.extract(request);

        String email = authInfo.getEmail();
        String password = authInfo.getPassword();

        cartService.addCart(productId, email, password);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCart(@PathVariable("id") Long cartId) {
        cartService.deleteCartById(cartId);
        return "cart";
    }
}
