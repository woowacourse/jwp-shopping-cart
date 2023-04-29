package cart.controller;

import cart.dto.ResponseProductDto;
import cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String index(final Model model) {
        final List<ResponseProductDto> responseProductDtos = cartService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        final List<ResponseProductDto> responseProductDtos = cartService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "admin";
    }
}
