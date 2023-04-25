package cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "admin";
    }
}
