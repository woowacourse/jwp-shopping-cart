package cart.controller.view;

import cart.dto.response.MemberResponse;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    private final MemberService memberService;

    public SettingsController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String settings(Model model) {
        List<MemberResponse> members = memberService.readAll();
        model.addAttribute("members", members);
        return "settings";
    }
}
