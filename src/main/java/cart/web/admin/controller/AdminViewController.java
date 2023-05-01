package cart.web.admin.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.domain.admin.service.CartService;
import cart.web.admin.dto.ProductResponse;

@Controller
public class AdminViewController {

    private final CartService cartService;

    public AdminViewController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/admin")
    public String getAllProducts(final Model model) {
        final List<ProductResponse> responses = cartService.findAll().stream()
            .map(ProductResponse::from)
            .collect(Collectors.toList());
        model.addAttribute("products", responses);
        return "admin";
    }
}
