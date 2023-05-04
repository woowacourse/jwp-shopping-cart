package cart.controller.setting;

import cart.dto.user.UserResponse;
import cart.service.user.UserQueryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingViewController {

    private final UserQueryService userQueryService;

    public SettingViewController(final UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @GetMapping("/settings")
    public String getSettingsPage(final Model model) {
        final List<UserResponse> userResponses = userQueryService.findAll()
                .stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());

        model.addAttribute("members", userResponses);
        return "settings";
    }
}
