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
public class SettingController {

    private final MemberService memberService;

    public SettingController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String getSettingsView(Model model) {
        List<MemberResponse> memberResponses = memberService.findAllMembers();
        model.addAttribute("members", memberResponses);
        return "settings";
    }
}
