package cart.controller;

import cart.domain.member.MemberEntity;
import cart.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class MemberViewController {

    private final MemberService memberService;

    public MemberViewController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String settingsPage(final Model model) {
        final List<MemberEntity> members = memberService.findAll();

        model.addAttribute("members", members);

        return "settings";
    }
}
