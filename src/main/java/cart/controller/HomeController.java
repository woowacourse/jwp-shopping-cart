package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
import java.util.List;
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
    public String getHomePage(final Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

}
