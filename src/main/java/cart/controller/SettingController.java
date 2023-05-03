package cart.controller;

import cart.domain.member.Member;
import cart.dto.MembersResponse;
import cart.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingController {

    private final MemberService memberService;

    public SettingController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String renderSettingPage(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", MembersResponse.of(members));
        return "settings";
    }
}
