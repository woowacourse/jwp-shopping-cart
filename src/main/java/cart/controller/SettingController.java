package cart.controller;


import cart.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingController {

    private final UserService userService;

    public SettingController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserLIst(final Model model) {
        model.addAttribute("users", userService.findAll());
        return "settings";
    }
}
