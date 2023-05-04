package cart.controller.view;

import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartViewController {
    private CartService cartService;

    public CartViewController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String productPageView() {
        return "cart";
    }
}
