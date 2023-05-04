package cart.web.controller;

import cart.domain.user.service.CartUserService;
import cart.domain.user.service.dto.CartUserDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingViewController {
    private final CartUserService cartUserService;

    public SettingViewController(CartUserService cartUserService) {
        this.cartUserService = cartUserService;
    }

    @GetMapping("/settings")
    public String loadSettingPage(Model model) {
        List<CartUserDto> allCartUsers = cartUserService.getAllCartUsers();

        model.addAttribute("cartUsers", allCartUsers);

        return "settings";
    }
}
