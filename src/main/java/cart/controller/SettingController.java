package cart.controller;

import cart.dao.UserResponses;
import cart.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingController {

    private final UserService userService;

    public SettingController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ModelAttribute("users")
    public UserResponses findUsersAll() {
        return userService.findAll();
    }
}
