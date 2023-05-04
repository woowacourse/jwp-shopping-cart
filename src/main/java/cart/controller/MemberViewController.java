package cart.controller;

import cart.dto.response.MemberResponse;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/settings")
public class MemberViewController {

    private final MemberService memberService;

    public MemberViewController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String settingsPage(final Model model) {
        final List<MemberResponse> members = memberService.findAll().stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());

        model.addAttribute("members", members);

        return "settings";
    }
}
