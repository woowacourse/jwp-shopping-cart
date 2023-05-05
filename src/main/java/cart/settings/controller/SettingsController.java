package cart.settings.controller;

import cart.settings.dto.UserResponseDTO;
import cart.settings.service.SettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(final SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping("/settings")
    public String settings(final Model model) {
        final List<UserResponseDTO> allUsers = this.settingsService.findAllUsers();
        model.addAttribute("users", allUsers);
        return "settings";
    }
}
