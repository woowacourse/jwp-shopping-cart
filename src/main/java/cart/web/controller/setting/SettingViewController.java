package cart.web.controller.setting;

import cart.domain.user.UserService;
import cart.domain.user.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/settings")
@Controller
public class SettingViewController {
    private final UserService userService;

    public SettingViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String loadSettingsPage(Model model) {
        List<UserDto> allUsers = userService.getAllUsers();

        model.addAttribute("users", allUsers);

        return "settings";
    }
}
