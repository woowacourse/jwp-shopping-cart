package cart.controller;

import cart.domain.Product;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class CartController {

    private final ProductService productService;

    public CartController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String indexPage(final Model model) {
        final List<Product> products = productService.findAll();

        model.addAttribute("products", products);

        return "index";
    }
}
