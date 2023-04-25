package cart.controller;

import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ProductService productService;

    public ViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/admin")
    public String getAdmin(Model model) {
        model.addAttribute("productsDto", productService.findAll());
        return "admin";
    }

    @GetMapping("/settings")
    public String getSettings() {
        return "settings";
    }

    @GetMapping("/cart")
    public String getCart() {
        return "cart";
    }
}
