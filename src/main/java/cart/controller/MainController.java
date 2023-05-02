package cart.controller;

import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final ProductService productService;
    private final MemberService memberService;

    public MainController(final ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String showHome(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @GetMapping("/settings")
    public String showSettings(final Model model) {
        model.addAttribute("members", memberService.findAll());
        return "settings";
    }

    @GetMapping("/cart")
    public String showCart() {
        return "cart";
    }
}
