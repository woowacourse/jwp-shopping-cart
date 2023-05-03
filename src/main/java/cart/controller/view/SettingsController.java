package cart.controller.view;

import cart.controller.dto.response.MemberResponse;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SettingsController {

    private final MemberService memberService;

    public SettingsController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        final List<MemberResponse> responses = memberService.findAll();
        model.addAttribute("members", responses);
        return "settings";
    }

}
