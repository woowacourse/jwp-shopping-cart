package cart.controller.view;

import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingPageController {
    private final MemberService memberService;

    public SettingPageController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String settingPage(Model model) {
        model.addAttribute("members", memberService.findAllMembers());
        return "settings";
    }
}
