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

    @Autowired
    public ViewController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("")
    public String productPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @GetMapping("/settings")
    public String settingPage(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "settings";
    }

}
