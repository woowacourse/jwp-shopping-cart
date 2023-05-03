package cart.controller;

import cart.auth.AuthenticateUser;
import cart.dto.AuthUser;
import cart.dto.CartResponses;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String cartHome() {
        return "cart";
    }

    @GetMapping("/users")
    public ResponseEntity<CartResponses> getCartsAllByUserId(@AuthenticateUser AuthUser user) {
        final CartResponses cartResponses = cartService.findAllByUserId(user.getId());
        return ResponseEntity.ok(cartResponses);
    }
}
