package cart.controller;

import cart.dto.ProductResponseDto;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String main(Model model) {
        List<ProductResponseDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

}
