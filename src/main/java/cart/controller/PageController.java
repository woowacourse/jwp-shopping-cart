package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final ProductService productService;

    public PageController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }

    @GetMapping("/products/{id}")
    public String product(final Model model) {
        final List<ProductDto> result = productService.findAll();
        model.addAttribute("product", result.get(0));
        return "product";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }
}
