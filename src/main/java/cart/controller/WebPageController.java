package cart.controller;

import cart.controller.dto.response.MemberResponse;
import cart.controller.dto.response.ProductResponse;
import cart.service.member.MemberService;
import cart.service.product.ProductReadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

    private final ProductReadService productReadService;
    private final MemberService memberService;

    public WebPageController(final ProductReadService productReadService, final MemberService memberService) {
        this.productReadService = productReadService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String renderStartPage(final Model model) {
        model.addAttribute("products", ProductResponse.mapProducts(productReadService.getAll()));
        return "index";
    }

    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", ProductResponse.mapProducts(productReadService.getAll()));
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
