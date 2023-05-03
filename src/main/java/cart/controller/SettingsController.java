package cart.controller;

import cart.controller.dto.MemberResponse;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SettingsController {

    private final MemberService memberService;

    public SettingsController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String setting(final Model model) {
        List<MemberResponse> memberResponses = memberService.findAll();
        model.addAttribute("members", memberResponses);
        return "settings";
    }
}
