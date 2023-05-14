package cart.controller.admin;

import cart.service.product.ProductService;
import cart.service.product.dto.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminViewController {

    private final ProductService productService;

    public AdminViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String productList(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

}
