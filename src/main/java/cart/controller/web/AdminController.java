package cart.controller.web;

import cart.domain.product.Product;
import cart.dto.ProductsResponse;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String renderAdminPage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", ProductsResponse.of(products));
        return "admin";
    }
}
