package cart.web.controller.admin;

import cart.domain.product.ProductService;
import cart.domain.product.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminViewController {
    private final ProductService productService;

    public AdminViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String loadAdminPage(Model model) {
        List<ProductDto> allProducts = productService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "admin";
    }
}
