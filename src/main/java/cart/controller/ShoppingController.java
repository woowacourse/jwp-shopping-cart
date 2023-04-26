package cart.controller;

import cart.controller.dto.ProductDto;
import cart.service.ShoppingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ShoppingController {

    private final ShoppingService shoppingService;

    public ShoppingController(final ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @GetMapping("/")
    public String index(final Model model) {
        final List<ProductDto> products = shoppingService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id, final Model model) {
        final ProductDto productDto = shoppingService.getById(id);
        model.addAttribute("product", productDto);
        return "product";
    }
}
