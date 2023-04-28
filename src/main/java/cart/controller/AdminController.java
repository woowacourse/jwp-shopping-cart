package cart.controller;

import cart.service.ProductQueryService;
import cart.service.dto.ProductSearchResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductQueryService productQueryService;

    public AdminController(final ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping
    public String searchProduct(final Model model) {
        List<ProductSearchResponse> productSearchResponses = productQueryService.searchAllProducts();
        model.addAttribute("products", productSearchResponses);
        return "admin";
    }
}
