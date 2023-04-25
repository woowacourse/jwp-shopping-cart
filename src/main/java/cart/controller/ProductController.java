package cart.controller;

import cart.domain.Product;
import cart.request.ProductRequest;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(final Model model) {
        final List<Product> products = productService.findAll();

        model.addAttribute("products", products);

        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        final List<Product> products = productService.findAll();

        model.addAttribute("products", products);

        return "admin";
    }

    @PostMapping("/admin")
    public void register(@RequestBody final ProductRequest productRequest) {
        productService.register(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
    }
}
