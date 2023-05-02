package cart.controller;

import java.util.List;

import cart.dto.UserResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/settings")
    public String getSettingsPage(final Model model) {
        final List<UserResponse> userResponses = List.of(
                new UserResponse("a@a.com", "password1"),
                new UserResponse("b@b.com", "password2")
        );
        model.addAttribute("members", userResponses);
        return "settings";
    }
}
