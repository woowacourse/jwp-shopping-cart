package cart.controller;

import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartPageController {

    private final MemberService memberService;

    public CartPageController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String settings(final Model model) {
        model.addAttribute("members", memberService.findAll());
        return "settings";
    }

    @GetMapping("/cartPage")
    public String cart() {
        return "cart";
    }
}
