package cart.controller;

import cart.domain.MemberRole;
import cart.service.MemberService;
import cart.service.dto.MemberResponse;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public ModelAndView getMembers() {
        final List<MemberResponse> memberResponses = memberService.getMembers();
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("settings");
        mv.addObject("members", memberResponses);
        return mv;
    }

    @GetMapping("/member/{memberId}")
    public ModelAndView getMember(@PathVariable Long memberId) {
        final MemberResponse memberResponse = memberService.getById(memberId);
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("member");
        mv.addObject("member", memberResponse);
        return mv;
    }

    @ModelAttribute("roles")
    public List<MemberRole> getRoles() {
        return List.of(MemberRole.values());
    }
}
