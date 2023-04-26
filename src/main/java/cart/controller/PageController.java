package cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
