package cart.controller;

import cart.dto.response.ProductResponse;
import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<ProductResponse> productsResponse = cartService.readAll();
        model.addAttribute("products", productsResponse);
        return "index";
    }
}
