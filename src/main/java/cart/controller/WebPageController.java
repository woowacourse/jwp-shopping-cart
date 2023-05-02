package cart.controller;

import cart.dto.ProductResponse;
import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

    private final CartService cartService;

    public WebPageController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String renderStartPage(final Model model) {
        model.addAttribute("products", ProductResponse.mapProducts(cartService.getAll()));
        return "index";
    }

    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", ProductResponse.mapProducts(cartService.getAll()));
        return "admin";
    }


}
