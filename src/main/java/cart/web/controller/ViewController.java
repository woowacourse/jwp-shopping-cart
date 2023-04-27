package cart.web.controller;

import cart.domain.product.service.CartService;
import cart.domain.product.service.dto.ProductDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    private final CartService cartService;

    public ViewController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String loadIndexPage(Model model) {
        List<ProductDto> allProducts = cartService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "index";
    }

    @GetMapping("/admin")
    public String loadAdminPage(Model model) {
        List<ProductDto> allProducts = cartService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "admin";
    }
}
