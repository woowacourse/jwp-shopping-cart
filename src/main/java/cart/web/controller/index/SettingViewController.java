package cart.web.controller.index;

import cart.domain.user.service.dto.CartUserResponseDto;
import cart.domain.user.usecase.FindAllCartUsersUseCase;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/settings")
@Controller
public class SettingViewController {
    private final FindAllCartUsersUseCase findAllCartUserService;

    public SettingViewController(final FindAllCartUsersUseCase findAllCartUserService) {
        this.findAllCartUserService = findAllCartUserService;
    }

    @GetMapping
    public String loadSettingPage(final Model model) {
        final List<CartUserResponseDto> allCartUsers = findAllCartUserService.getAllCartUsers();

        model.addAttribute("cartUsers", allCartUsers);

        return "settings";
    }
}
