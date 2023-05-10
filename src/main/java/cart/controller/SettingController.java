package cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cart.dto.user.UserResponse;
import cart.service.UserService;

@Controller
@RequestMapping("/settings")
public class SettingController {

    private final UserService userService;

    public SettingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String setting(final Model model) {
        final List<UserResponse> users = userService.findAll();
        model.addAttribute("members", users);
        return "settings";
    }
}
