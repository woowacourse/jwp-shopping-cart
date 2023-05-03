package cart.controller;


import cart.service.ProductService;
import cart.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    private final ProductService productService;
    private final UserService userService;

    public CartController(final ProductService productService, final UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showProductsList(final Model model) {
        model.addAttribute("products", productService.findProducts());
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(final Model model) {
        model.addAttribute("products", productService.findProducts());
        return "admin";
    }

    @GetMapping("/cart")
    public String showCart(final Model model) {
        return "cart";
    }

    @GetMapping("/settings")
    public String showUsers(final Model model) {
        model.addAttribute("members", userService.findAllUsers());
        return "settings";
    }
}
