package cart.controller.member;

import cart.dto.MemberDto;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MemberPageController {

    private final MemberService memberService;

    public MemberPageController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String readRegisterPage() {
        return "register";
    }

    @GetMapping("/settings")
    public String readLoginPage(final Model model) {
        List<MemberDto> members = memberService.findAllMember();
        model.addAttribute("members", members);
        return "settings";
    }
}
