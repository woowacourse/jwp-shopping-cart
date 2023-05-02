package cart.controller.view;

import cart.dto.response.MemberResponse;
import cart.service.MembersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/settings")
public class SettingsController {

    private final MembersService membersService;

    public SettingsController(MembersService membersService) {
        this.membersService = membersService;
    }

    @GetMapping
    public String settings(Model model) {
        List<MemberResponse> members = membersService.readAll();
        model.addAttribute("members", members);
        return "settings";
    }
}
