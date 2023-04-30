package cart.controller;

import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showHome(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }
}
