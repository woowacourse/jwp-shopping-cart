package cart.controller.view;

import cart.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class SettingsPageController {
    private final MemberService memberService;

    @GetMapping("/settings")
    public String settingsPage(Model model) {
        model.addAttribute("members", memberService.findAll());
        return "settings";
    }
}
