package cart.controller;


import cart.dto.response.UserResponseDto;
import cart.entity.ProductEntity;
import cart.service.ProductService;
import cart.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final ProductService productService;
    private final UserService userService;

    public MainController(final ProductService productService, final UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String rootPage(final Model model) {
        final List<ProductEntity> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "index";
    }

    @GetMapping("/cart")
    public String cartPage() {
        return "cart";
    }

    @GetMapping("/settings")
    public String settingsPage(final Model model) {
        final List<UserResponseDto> userResponseDto = userService.findAll();
        model.addAttribute("members", userResponseDto);
        return "settings";
    }

    @GetMapping("/admin")
    public String adminPage(final Model model) {
        final List<ProductEntity> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "admin";
    }
}
