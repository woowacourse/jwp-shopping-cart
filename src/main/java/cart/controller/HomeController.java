package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.dto.ProductResponse;
import cart.service.JwpCartService;

@Controller
public class HomeController {

    private final JwpCartService jwpCartService;

    public HomeController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponse> all = jwpCartService.findAllProducts();
        model.addAttribute("products", all);
        return "index";
    }
}
