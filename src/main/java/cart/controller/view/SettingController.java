package cart.controller.view;

import cart.dao.MemberDao;
import cart.domain.Member;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingController {

    private final MemberDao memberDao;

    public SettingController(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @GetMapping("/settings")
    String setting(final Model model) {
        final List<Member> all = memberDao.findAll();
        model.addAttribute("members", all);
        return "settings";
    }
}
