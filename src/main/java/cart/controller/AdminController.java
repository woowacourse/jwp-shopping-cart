package cart.controller;

import cart.service.ProductService;
import cart.service.dto.ProductSearchResponse;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String searchProduct(final Model model) {
        List<ProductSearchResponse> productSearchResponses = productService.searchAllProducts();
        model.addAttribute("products", productSearchResponses);
        return "admin";
    }
}
