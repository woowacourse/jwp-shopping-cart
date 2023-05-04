package cart.controller;

import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ProductService productService;
    private final MemberService memberService;

    public ViewController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String getHome(final Model model) {
        model.addAttribute("productsDto", productService.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String getAdmin(final Model model) {
        model.addAttribute("productsDto", productService.findAll());
        return "admin";
    }

    @GetMapping("/settings")
    public String getSettings(final Model model) {
        model.addAttribute("membersDto", memberService.findAll());
        return "settings";
    }

    @GetMapping("/cart")
    public String getCart() {
        return "cart";
    }
}
