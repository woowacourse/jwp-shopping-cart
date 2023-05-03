package cart.controller;

import cart.dto.response.MemberResponse;
import cart.dto.response.ProductResponse;
import cart.service.MemberService;
import cart.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final ProductService productService;
    private final MemberService memberService;

    @Autowired
    public PageController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String readMainPage(final Model model) {
        final List<ProductResponse> productResponses = productService.findAll();
        model.addAttribute("products", productResponses);
        return "index";
    }

    @GetMapping("/admin")
    public String readAdminPage(final Model model) {
        final List<ProductResponse> productResponses = productService.findAll();
        model.addAttribute("products", productResponses);
        return "admin";
    }

    @GetMapping("/settings")
    public String readSettingPage(final Model model) {
        final List<MemberResponse> memberResponse = memberService.findAll();
        model.addAttribute("members", memberResponse);
        return "settings";
    }

    @GetMapping("/cart")
    public String readCartPage() {
        return "cart";
    }
}
