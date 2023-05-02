package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class PageController {

    private final ProductService productService;

    public PageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String productPageView(Model model) {
        List<ProductDto> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String adminPageView(Model model) {
        List<ProductDto> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "admin";
    }


}
