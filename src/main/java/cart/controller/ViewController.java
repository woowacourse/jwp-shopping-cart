package cart.controller;

import cart.dto.ProductDto;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {
    private final ProductService productService;

    public ViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String home(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("admin")
    public String productList(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }
}
