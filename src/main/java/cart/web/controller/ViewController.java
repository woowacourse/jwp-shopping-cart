package cart.web.controller;

import cart.domain.service.ProductService;
import cart.domain.service.UserService;
import cart.domain.service.dto.ProductDto;
import cart.domain.service.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {
    private final ProductService productService;
    private final UserService userService;

    public ViewController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String loadIndexPage(Model model) {
        List<ProductDto> allProducts = productService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "index";
    }

    @GetMapping("/admin")
    public String loadAdminPage(Model model) {
        List<ProductDto> allProducts = productService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "admin";
    }

    @GetMapping("/settings")
    public String loadSettingsPage(Model model) {
        List<UserDto> allUsers = userService.getAllUsers();

        model.addAttribute("users", allUsers);

        return "settings";
    }
}
