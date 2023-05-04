package cart.controller;


import cart.domain.MemberEntity;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SettingsController {
    private final MemberService memberService;

    public SettingsController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String settings(Model model) {

        List<MemberEntity> members = memberService.findMembers();

        model.addAttribute("members", members);
        return "settings.html";
    }
}
