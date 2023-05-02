package cart.controller;

import cart.domain.member.Member;
import cart.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberViewController {

    private final MemberService memberService;

    public MemberViewController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String settingsPage(final Model model) {
        final List<Member> members = memberService.fetchAll();

        model.addAttribute("members", members);

        return "settings";
    }
}
