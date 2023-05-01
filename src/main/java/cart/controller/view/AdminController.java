package cart.controller.view;

import cart.dto.response.ProductResponse;
import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CartService cartService;

    public AdminController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String admin(Model model) {
        List<ProductResponse> productsResponse = cartService.readAll();
        model.addAttribute("products", productsResponse);
        return "admin";
    }
}
