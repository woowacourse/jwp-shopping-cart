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

    @GetMapping("")
    public String addProduct(Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

}
