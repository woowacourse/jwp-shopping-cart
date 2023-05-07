package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.dto.MemberResponse;
import cart.dto.ProductResponse;
import cart.service.JwpCartService;

@Controller
public class PageController {
    private final JwpCartService jwpCartService;

    public PageController(JwpCartService jwpCartService) {
        this.jwpCartService = jwpCartService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponse> all = jwpCartService.findAllProducts();
        model.addAttribute("products", all);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> all = jwpCartService.findAllProducts();
        model.addAttribute("products", all);
        return "admin";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        List<MemberResponse> members = jwpCartService.findAllMembers();
        model.addAttribute("members", members);
        return "settings";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
