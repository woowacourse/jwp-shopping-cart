package cart.controller;

import cart.controller.dto.ProductDto;
import cart.service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String main(final Model model) {
        final List<ProductDto> productDtos = cartService.findAll();
        model.addAttribute("products", productDtos);
        return "index";
    }
}
