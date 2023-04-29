package cart.controller;

import cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {

    private final ProductService productService;

    @Autowired
    public ProductViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/")
    public String home(Model model) {
        model.addAttribute("products", productService.getAllProducts());

        return "index";
    }

    @GetMapping(path = "/admin")
    public String admin(Model model) {
        model.addAttribute("products", productService.getAllProducts());

        return "admin";
    }
}
