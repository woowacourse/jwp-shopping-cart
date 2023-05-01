package cart.web.settings.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.domain.settings.service.MemberService;
import cart.web.settings.dto.MemberResponse;

@Controller
public class SettingsController {

    private final MemberService memberService;

    public SettingsController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String getAllMembers(final Model model) {
        final List<MemberResponse> members = memberService.findAll().stream()
            .map(MemberResponse::from)
            .collect(Collectors.toList());
        model.addAttribute("members", members);
        return "settings";
    }
}
