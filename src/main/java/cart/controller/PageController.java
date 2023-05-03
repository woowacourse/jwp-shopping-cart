package cart.controller;

import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    private final ProductService productService;
    private final MemberService memberService;

    public PageController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }
    
    @GetMapping("/")
    public String renderProductListPage(final Model model) {
        model.addAttribute("products", productService.display());
        return "index";
    }
    
    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", productService.display());
        return "admin";
    }

    @GetMapping("/settings")
    public String renderSettingsPage(final Model model) {
        model.addAttribute("members", memberService.display());
        return "settings";
    }
}
