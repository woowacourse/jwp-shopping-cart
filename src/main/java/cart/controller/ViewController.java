package cart.controller;


import cart.entity.ProductEntity;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final ProductService productService;

    public ViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String adminPage(final Model model) {
        final List<ProductEntity> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "admin";
    }

    @GetMapping
    public String rootPage(Model model) {
        final List<ProductEntity> allProducts = productService.findAll();
        model.addAttribute("products", allProducts);
        return "index";
    }
}
