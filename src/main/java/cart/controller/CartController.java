package cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}
