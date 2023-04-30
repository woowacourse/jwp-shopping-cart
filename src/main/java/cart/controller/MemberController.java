package cart.controller;

import cart.controller.dto.MemberDto;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String getMembers(final Model model) {
        final List<MemberDto> memberDtos = memberService.getMembers();
        model.addAttribute("members", memberDtos);
        return "settings";
    }

    @GetMapping("/member/{memberId}")
    public String getMember(@PathVariable Long memberId, final Model model) {
        final MemberDto memberDto = memberService.getById(memberId);
        model.addAttribute("member", memberDto);
        return "member";
    }
}
