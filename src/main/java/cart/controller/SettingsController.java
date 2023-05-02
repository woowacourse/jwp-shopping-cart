package cart.controller;

import cart.controller.dto.MemberRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SettingsController {

    @GetMapping("/settings")
    public String setting(final Model model) {
        model.addAttribute("members", List.of(new MemberRequest("a@a.com", "password1")));
        return "settings";
    }
}
