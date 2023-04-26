package cart.controller;

import cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminPageController {
    private final ProductService productService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "admin";
    }
}
