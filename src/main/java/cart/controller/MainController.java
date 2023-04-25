package cart.controller;

import cart.dto.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String hello(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }
}
