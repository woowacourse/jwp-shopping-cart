package cart.controller;

import cart.controller.dto.response.UserResponse;
import cart.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public final class SettingController {

    private final UserService userService;

    public SettingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String setting(Model model) {
        List<UserResponse> userResponses = userService.findAll();
        model.addAttribute("members", userResponses);
        return "settings";
    }
}
