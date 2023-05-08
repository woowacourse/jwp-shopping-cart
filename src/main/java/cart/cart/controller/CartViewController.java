package cart.cart.controller;

import cart.cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cart")
public class CartViewController {
    private final CartService cartService;

    public CartViewController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String showCart() {
        return "cart";
    }
}
