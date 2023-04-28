package cart.controller;

import cart.dto.ProductResponseDto;
import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String index(Model model) {
        final List<ProductResponseDto> products = cartService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("admin")
    public String admin(Model model) {
        final List<ProductResponseDto> products = cartService.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }
}
