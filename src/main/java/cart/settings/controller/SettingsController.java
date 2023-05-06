package cart.settings.controller;

import cart.auth.dto.UserResponseDTO;
import cart.auth.service.AuthService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController {
    
    private final AuthService authService;
    
    public SettingsController(final AuthService authService) {
        this.authService = authService;
    }
    
    @GetMapping("/settings")
    public String settings(final Model model) {
        final List<UserResponseDTO> allUsers = this.authService.findAllUsers();
        model.addAttribute("users", allUsers);
        return "settings";
    }
}
