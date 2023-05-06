package cart.controller;

import cart.domain.product.Product;
import cart.domain.user.User;
import cart.service.ProductService;
import cart.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Transactional
@Controller
public class ViewController {

    private final ProductService productService;
    private final UserService userService;

    public ViewController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String viewHome(final Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String viewAdmin(final Model model) {
        final List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/users")
    public String viewUsers(final Model model) {
        final List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }
}
