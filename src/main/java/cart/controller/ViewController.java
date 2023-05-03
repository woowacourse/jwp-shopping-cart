package cart.controller;


import cart.entity.ProductEntity;
import cart.service.CustomerService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    public final CustomerService customerService;
    private final ProductService productService;

    public ViewController(final ProductService productService, final CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
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

    @GetMapping("/settings")
    public String settingPage(Model model) {
        model.addAttribute("members", customerService.findAll());
        return "settings";
    }
}
