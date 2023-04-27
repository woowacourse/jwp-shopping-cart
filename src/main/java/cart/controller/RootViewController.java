package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cart.dto.ProductResponse;
import cart.service.CartService;

@Controller
@RequestMapping("/")
public class RootViewController {

    private final CartService cartService;

    public RootViewController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getProductList(final Model model) {
        List<ProductResponse> products = cartService.readAll();
        model.addAttribute("products", products);
        return "index";
    }
}
