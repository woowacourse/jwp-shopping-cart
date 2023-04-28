package cart.controller;

import cart.controller.dto.ProductDto;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ShoppingController {

    private final ProductService productService;

    public ShoppingController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(final Model model) {
        final List<ProductDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/{productId}")
    public String getProduct(@PathVariable Long productId, final Model model) {
        final ProductDto productDto = productService.getById(productId);
        model.addAttribute("product", productDto);
        return "product";
    }
}
