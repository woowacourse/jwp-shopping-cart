package cart.controller;

import cart.domain.product.Product;
import cart.dto.ProductsResponse;
import cart.service.ProductService;
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

    @GetMapping
    public String renderHomePage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", ProductsResponse.of(products));
        return "index";
    }
}
