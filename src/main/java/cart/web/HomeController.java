package cart.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.domain.admin.service.CartService;
import cart.web.admin.dto.ProductResponse;

@Controller
public class HomeController {

    private final CartService cartService;

    public HomeController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String getAllProducts(final Model model) {
        final List<ProductResponse> response = cartService.findAll().stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
        model.addAttribute("products", response);
        return "index";
    }
}
