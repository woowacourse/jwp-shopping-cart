package cart.controller;

import cart.dto.ProductResponse;
import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class RootController {

    private final CartService cartService;

    public RootController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getProductList(final Model model) {
        List<ProductResponse> products = cartService.read();
        model.addAttribute("products", products);
        return "index";
    }
}
