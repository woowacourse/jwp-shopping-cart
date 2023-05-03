package cart.controller;

import cart.domain.product.ProductEntity;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String indexPage(final Model model) {
        final List<ProductEntity> products = productService.findAll();

        model.addAttribute("products", products);

        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(final Model model) {
        final List<ProductEntity> products = productService.findAll();

        model.addAttribute("products", products);

        return "admin";
    }
}
