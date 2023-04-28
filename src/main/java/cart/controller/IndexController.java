package cart.controller;

import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final ProductService productService;

    public IndexController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }

}
