package cart.controller;

import cart.controller.dto.response.MemberResponse;
import cart.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingController {

    private final MemberService memberService;

    public SettingController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String getSettingPage(final Model model) {
        List<MemberResponse> findAllMember = memberService.findAllMember();
        model.addAttribute("members", findAllMember);
        return "settings";
    }

}
