package cart.product.controller;

import cart.product.service.MemberService;
import cart.product.service.ProductListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    private final ProductListService productListService;
    private final MemberService memberService;

    public PageController(final ProductListService productListService, final MemberService memberService) {
        this.productListService = productListService;
        this.memberService = memberService;
    }
    
    @GetMapping("/")
    public String renderProductListPage(final Model model) {
        model.addAttribute("products", productListService.display());
        return "index";
    }
    
    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", productListService.display());
        return "admin";
    }

    @GetMapping("/settings")
    public String renderSettingsPage(final Model model) {
        model.addAttribute("members", memberService.display());
        return "settings";
    }
}
