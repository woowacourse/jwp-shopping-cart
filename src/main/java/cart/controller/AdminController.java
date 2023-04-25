package cart.controller;

import cart.controller.dto.ProductDto;
import cart.service.ShoppingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ShoppingService shoppingService;

    public AdminController(final ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping
    public String addProduct(@ModelAttribute final ProductDto productDto) {
        shoppingService.save(productDto);
        return "admin";
    }
}
