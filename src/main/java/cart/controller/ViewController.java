package cart.controller;

import cart.service.ProductService;
import cart.service.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final ProductService productService;

    public ViewController(final ProductService productService) {
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

    @GetMapping(path = "/settings")
    public String settings(Model model){
        return "settings";
    }

    @GetMapping(path = "/cart")
    public String cart(Model model){
        return "cart";
    }
}
