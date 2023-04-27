package cart.controller;

import cart.service.CartService;
import cart.service.dto.ProductResponse;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CartService cartService;

    public HomeController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String showAllProducts(Model model) {
        List<ProductResponse> allProducts = cartService.findAllProducts();
        model.addAttribute("products", allProducts);
        return "index";
    }
}
