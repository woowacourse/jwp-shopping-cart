package cart.web.controller.cart;

import cart.domain.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartViewController {

    private final CartService cartService;

    public CartViewController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String renderCart() {
        return "cart.html";
    }
}
