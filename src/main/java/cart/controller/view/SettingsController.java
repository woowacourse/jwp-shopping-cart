package cart.controller.view;

import cart.controller.dto.response.MemberResponse;
<<<<<<< HEAD
=======
import cart.dao.MemberDao;
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

<<<<<<< HEAD
=======
import java.lang.reflect.Member;
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
import java.util.List;

@Controller
public class SettingsController {

    private final MemberService memberService;

    public SettingsController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
<<<<<<< HEAD
    public String getSettingsPage(Model model) {
=======
    public String settings(Model model) {
>>>>>>> 1eb8f466 (feat: 모든 사용자의 정보를 확인하고 사용자를 선택할 수 있다.)
        final List<MemberResponse> responses = memberService.findAll();
        model.addAttribute("members", responses);
        return "settings";
    }

}
