package cart.controller;

import cart.dto.ProductResponse;
import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final CartService cartService;

    public ViewController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String getIndex(final Model model) {
        List<ProductResponse> products = cartService.read();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String getAdmin(final Model model) {
        List<ProductResponse> products = cartService.read();
        model.addAttribute("products", products);
        return "admin";
    }
}
