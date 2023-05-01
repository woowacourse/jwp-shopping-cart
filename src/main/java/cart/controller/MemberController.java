package cart.controller;

import cart.controller.dto.MemberDto;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public ModelAndView getMembers() {
        final List<MemberDto> memberDtos = memberService.getMembers();
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("settings");
        mv.addObject("members", memberDtos);
        return mv;
    }

    @GetMapping("/member/{memberId}")
    public ModelAndView getMember(@PathVariable Long memberId, final Model model) {
        final MemberDto memberDto = memberService.getById(memberId);
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("member");
        mv.addObject("member", memberDto);
        return mv;
    }
}
