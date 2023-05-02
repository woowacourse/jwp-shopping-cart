package cart.controller;

import cart.entity.Member;
import cart.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/settings")
@Controller
public class SettingsController {

    private final MemberService memberService;

    public SettingsController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String userList(final Model model) {
        final List<Member> allMember = memberService.findAllMember();
        model.addAttribute("members", allMember);
        return "/settings";
    }
}
