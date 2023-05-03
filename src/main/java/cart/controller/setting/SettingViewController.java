package cart.controller.setting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingViewController {

    @GetMapping("/settings")
    public String getSettingsPage() {
        return "settings";
    }
}
