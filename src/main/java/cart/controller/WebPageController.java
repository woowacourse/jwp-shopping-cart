package cart.controller;

import cart.controller.dto.MemberResponse;
import cart.controller.dto.ProductResponse;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

    private final ProductService productService;
    private final MemberService memberService;

    public WebPageController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String renderStartPage(final Model model) {
        model.addAttribute("products", ProductResponse.mapProducts(productService.getAll()));
        return "index";
    }

    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", ProductResponse.mapProducts(productService.getAll()));
        return "admin";
    }

    @GetMapping("/settings")
    public String renderMemberPage(final Model model) {
        model.addAttribute("members", MemberResponse.mapMembers(memberService.getMembers()));
        return "settings";
    }

    @GetMapping("/cart")
    public String renderCartPage() {
        return "cart";
    }
}
