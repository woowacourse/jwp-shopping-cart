package cart.web.controller.user;

import cart.domain.user.UserService;
import cart.web.controller.user.dto.UserResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserViewController {

    private final UserService userService;

    public UserViewController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String renderSettings(final Model model) {
        final List<UserResponse> userResponses = userService.getUsers();
        model.addAttribute("members", userResponses);
        return "settings.html";
    }
}
