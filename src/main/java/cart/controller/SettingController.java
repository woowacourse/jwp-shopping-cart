package cart.controller;

import cart.domain.Member;
import cart.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@AllArgsConstructor
public class SettingController {

    private final MemberService memberService;

    @GetMapping("/settings")
    public String settingPage(final Model model) {
        final List<Member> members = memberService.findAll();

        model.addAttribute("members", members);

        return "settings";
    }
}
