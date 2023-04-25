package cart.controller;

import cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "admin";
    }
}
