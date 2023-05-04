package cart.controller;

import cart.dto.MemberDto;
import cart.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController {

    private final MemberService memberService;

    public SettingsController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String readSettings(final Model model) {
        final List<MemberDto> allMember = memberService.findAllMember();
        model.addAttribute("members", allMember);
        return "settings";
    }
}
