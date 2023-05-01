package cart.web.controller;

import cart.domain.user.User;
import cart.domain.user.UserService;
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
        final List<User> users = userService.getUsers();
        model.addAttribute("members", users);
        return "settings.html";
    }
}
