package cart.controller;

import cart.service.dto.ProductDto;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    private final ProductService productService;

    public WebController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/")
    public String main(final Model model) {
        final List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping(path = "/admin")
    public String admin(Model model) {
        final List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }
}
