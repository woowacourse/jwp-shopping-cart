package cart.controller;

import cart.dto.ProductResponse;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String home(final Model model) {
        final List<ProductResponse> productResponses = productService.findAll();
        model.addAttribute("products", productResponses);

        return "index";
    }
}
