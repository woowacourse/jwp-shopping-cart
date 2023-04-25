package cart.controller;

import cart.service.CartService;
import cart.service.dto.ProductDto;
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

    @GetMapping("/product")
    public String showAllProducts(Model model) {
        List<ProductDto> allProducts = cartService.findAllProducts();
        model.addAttribute("products", allProducts);
        return "index";
    }
}
