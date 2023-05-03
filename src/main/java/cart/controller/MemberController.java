package cart.controller;

import cart.domain.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String membersPage(Model model) {
        List<MemberResponse> members = memberService.findAllMembers().stream()
                .map(m -> new MemberResponse(m.getId(), m.getEmail(), m.getPassword()))
                .collect(Collectors.toList());

        model.addAttribute("members", members);
        return "settings";
    }

}
