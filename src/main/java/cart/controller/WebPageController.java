package cart.controller;

import cart.domain.Product;
import cart.dto.ProductResponse;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebPageController {

    private final ProductService productService;

    public WebPageController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String renderStartPage(final Model model) {
        model.addAttribute("products", mapProducts(productService.getAll()));
        return "index";
    }

    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", mapProducts(productService.getAll()));
        return "admin";
    }

    private List<ProductResponse> mapProducts(List<Product> products) {
        return products.stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getImage(),
                        product.getPrice())
                ).collect(Collectors.toList());
    }
}
