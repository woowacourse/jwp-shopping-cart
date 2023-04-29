package cart.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingPageController {

    @GetMapping("/settings")
    public String settingPage() {
        return "settings";
    }
}
