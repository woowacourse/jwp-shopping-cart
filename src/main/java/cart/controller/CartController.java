package cart.controller;

import cart.dto.ProductDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CartController {

    @GetMapping
    public String run(Model model) {
        List<ProductDto> products = new ArrayList<>();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin";
    }
}
