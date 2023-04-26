package cart.web;

import cart.domain.product.CartService;
import cart.domain.product.dto.ProductDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String loadIndexPage(Model model) {
        List<ProductDto> allProducts = cartService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "index";
    }
}
