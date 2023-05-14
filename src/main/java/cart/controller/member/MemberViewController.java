package cart.controller.member;

import cart.controller.member.dto.MemberResponse;
import cart.service.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/settings")
public class MemberViewController {

    private final MemberService memberService;

    public MemberViewController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String membersPage(Model model) {
        List<MemberResponse> members = memberService.findAllMembers().stream()
                .map(m -> new MemberResponse(m.getId(), m.getEmail(), m.getPassword()))
                .collect(Collectors.toList());

        model.addAttribute("members", members);
        return "settings";
    }

}
