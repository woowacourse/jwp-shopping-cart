package cart.controller;

import cart.service.ProductService;
import cart.service.dto.ProductResponse;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showAllProducts(Model model) {
        List<ProductResponse> allProducts = productService.findAllProducts();
        model.addAttribute("products", allProducts);
        return "index";
    }
}
