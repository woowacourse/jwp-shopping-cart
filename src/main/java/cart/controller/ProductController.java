package cart.controller;

import cart.response.ProductResponse;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = {"/products", "/"})
    public String indexPage(final Model model) {
        final List<ProductResponse> products = productService.findAll();

        model.addAttribute("products", products);

        return "index";
    }
}
