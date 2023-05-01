package cart.web.controller.admin;

import cart.domain.product.ProductService;
import cart.web.controller.product.dto.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminViewController {

    private final ProductService productService;

    public AdminViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String renderAdmin(final Model model) {
        final List<ProductResponse> products = productService.getProducts();
        model.addAttribute("products", products);
        return "admin.html";
    }
}
