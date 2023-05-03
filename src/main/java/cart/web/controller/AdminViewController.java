package cart.web.controller;

import cart.domain.product.service.ProductService;
import cart.domain.product.service.dto.ProductDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {
    private final ProductService productService;

    public AdminViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String loadAdminPage(Model model) {
        List<ProductDto> allProducts = productService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "admin";
    }
}
