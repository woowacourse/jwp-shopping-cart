package cart.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsPageController {

    @GetMapping("/settings")
    public String settingsPage() {
        return "settings";
    }
}
