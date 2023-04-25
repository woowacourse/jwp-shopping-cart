package cart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping
    public String loadIndexPage() {
        return "/index";
    }
}
