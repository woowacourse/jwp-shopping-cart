package cart.web.controller;

import cart.domain.service.ProductService;
import cart.domain.service.dto.ProductDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    private final ProductService productService;

    public ViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String loadIndexPage(Model model) {
        List<ProductDto> allProducts = productService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "index";
    }

    @GetMapping("/admin")
    public String loadAdminPage(Model model) {
        List<ProductDto> allProducts = productService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "admin";
    }
}
