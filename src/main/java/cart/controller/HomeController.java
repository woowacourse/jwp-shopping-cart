package cart.controller;

import cart.service.ProductService;
import cart.controller.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String showAllProducts(Model model) {
        List<ProductResponse> allProducts = productService.findAllProducts()
                .stream()
                .map(ProductResponse::fromDto)
                .collect(Collectors.toList());
        model.addAttribute("products", allProducts);
        return "index";
    }
}
