package cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controller {

    @GetMapping("/products")
    public String products() {
        return "index";
    }

    @GetMapping("admin")
    public String admin() {
        return "admin";

    }
}
