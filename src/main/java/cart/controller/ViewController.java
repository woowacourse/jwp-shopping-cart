package cart.controller;

import cart.domain.product.Product;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Transactional
@Controller
public class ViewController {

    private final ProductService productService;

    public ViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String viewHome(final Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String viewAdmin(final Model model) {
        final List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }
}
