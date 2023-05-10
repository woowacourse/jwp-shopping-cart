package cart.controller;

import cart.dto.ProductResponseDto;
import cart.dto.UserResponseDto;
import cart.service.CartService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ViewController {

    private final ProductService productService;
    private final CartService cartService;

    public ViewController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping
    public String index(Model model) {
        final List<ProductResponseDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("admin")
    public String admin(Model model) {
        final List<ProductResponseDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("settings")
    public String setting(Model model) {
        final List<UserResponseDto> users = cartService.getUsers();
        model.addAttribute("members", users);
        return "settings";
    }

    @GetMapping("cart")
    public String cart() {
        return "cart";
    }
}
