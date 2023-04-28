package cart.controller;

import cart.dto.response.ResponseProductDto;
import cart.service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final CartService cartService;

    @Autowired
    public PageController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String readMainPage(final Model model) {
        final List<ResponseProductDto> responseProductDtos = cartService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "index";
    }

    @GetMapping("/admin")
    public String readAdminPage(final Model model) {
        final List<ResponseProductDto> responseProductDtos = cartService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "admin";
    }
}
