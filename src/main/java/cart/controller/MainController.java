package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.dto.ProductResponse;
import cart.service.ProductService;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(final Model model) {
        final List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }
}
