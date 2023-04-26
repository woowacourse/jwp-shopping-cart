package cart.controller;

import cart.service.ProductQueryService;
import cart.service.dto.ProductSearchResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WelcomePageController {

    private final ProductQueryService productQueryService;

    public WelcomePageController(final ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping("/")
    public String showWelcomePage(final Model model) {
        List<ProductSearchResponse> productSearchResponses = productQueryService.searchAllProducts();
        model.addAttribute("products", productSearchResponses);
        return "index";
    }
}
