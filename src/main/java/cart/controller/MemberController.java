package cart.controller;

import cart.persistance.dao.MemberDao;
import cart.persistance.entity.user.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public final class MemberController {

    private final MemberDao memberDao;

    public MemberController(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @GetMapping("/settings")
    public String members(final Model model) {
        final List<Member> members = memberDao.findAll();
        model.addAttribute("members", members);
        return "settings";
    }
}
