package cart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.controller.dto.MemberResponse;
import cart.dao.MemberDao;
import cart.domain.Member;

@Controller
public class MemberController {

    private final MemberDao memberDao;

    public MemberController(final MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @GetMapping("/settings")
    public String findAllMembers(final Model model) {
        List<Member> members = memberDao.findAll();
        List<MemberResponse> memberResponse = members.stream()
                .map(member -> new MemberResponse(member.getEmail(), member.getPassword()))
                .collect(Collectors.toList());
        model.addAttribute("members", memberResponse);

        return "settings";
    }
}
