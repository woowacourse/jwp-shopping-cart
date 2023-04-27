package cart.controller;


import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String showProductsList(Model model) {
        model.addAttribute("products", cartService.findProducts());
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        model.addAttribute("products", cartService.findProducts());
        return "admin";
    }
}
