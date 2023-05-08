package cart.controller;

import cart.dto.ProductResponse;
import cart.dto.UserResponse;
import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final CartService cartService;

    public ViewController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String getIndexView(final Model model) {
        List<ProductResponse> products = cartService.readAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String getAdminView(final Model model) {
        List<ProductResponse> products = cartService.readAllProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String getSettingsView(final Model model) {
        List<UserResponse> users = cartService.readAllUsers();
        model.addAttribute("members", users);
        return "settings";
    }

    @GetMapping("/cart")
    public String getCartView() {
        return "cart";
    }
}
