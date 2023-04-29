package cart.controller;

import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    private final ProductService productService;

    public CartController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/")
    public String home(Model model) {
        model.addAttribute("products", productService.findAll());

        return "index";
    }

    @GetMapping(path = "/admin")
    public String admin(Model model) {
        model.addAttribute("products", productService.findAll());

        return "admin";
    }
}
