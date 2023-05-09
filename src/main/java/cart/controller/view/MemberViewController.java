package cart.controller.view;

import cart.dto.MemberDto;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MemberViewController {

    private MemberService memberService;

    public MemberViewController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/settings")
    public String productPageView(Model model) {
        List<MemberDto> members = memberService.findAll();
        model.addAttribute("members", members);
        return "settings";
    }

}
