package cart.controller.view;

import cart.dao.MemberDao;
import cart.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberDao memberDao;

    public MemberController(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @GetMapping("/settings")
    String settings(final Model model) {
        final List<Member> all = memberDao.findAll();
        model.addAttribute("members", all);
        return "settings";
    }
}
