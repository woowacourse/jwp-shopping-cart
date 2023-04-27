package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.dto.ProductResponse;
import cart.service.CartService;

@Controller
public class AdminViewController {

    private final CartService cartService;

    public AdminViewController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/admin")
    public String getProductList(final Model model) {
        List<ProductResponse> products = cartService.readAll();
        model.addAttribute("products", products);
        return "admin";
    }
}
