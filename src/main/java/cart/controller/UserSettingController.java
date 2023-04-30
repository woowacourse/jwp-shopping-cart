package cart.controller;

import cart.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserSettingController {
    private final MemberRepository memberRepository;

    public UserSettingController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/settings")
    public String findAll(Model model) {
        model.addAttribute("members", memberRepository.findAll());
        return "settings";
    }
}
