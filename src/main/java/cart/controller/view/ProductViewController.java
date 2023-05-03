package cart.controller.view;

import cart.service.product.ProductService;
import cart.service.response.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductViewController {
    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showProducts(Model model) {
        final List<ProductResponse> findAllProducts = productService.findAll();
        model.addAttribute("products", findAllProducts);
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        final List<ProductResponse> findAllProducts = productService.findAll();
        model.addAttribute("products", findAllProducts);
        return "admin";
    }
}
