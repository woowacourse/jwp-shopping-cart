package cart.controller;

import cart.service.ProductService;
import cart.service.dto.ProductSearchResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WelcomePageController {

    private final ProductService productService;

    public WelcomePageController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showWelcomePage(final Model model) {
        List<ProductSearchResponse> productSearchResponses = productService.searchAllProducts();
        model.addAttribute("products", productSearchResponses);
        return "index";
    }
}
