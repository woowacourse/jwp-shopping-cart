package cart.controller;

import cart.controller.dto.ItemResponse;
import cart.controller.dto.UserResponse;
import cart.service.CartService;
import cart.service.ItemService;
import cart.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ItemService itemService;
    private final UserService userService;
    private final CartService cartService;

    public ViewController(final ItemService itemService, final UserService userService, final CartService cartService) {
        this.itemService = itemService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping
    public String redirectHomePage(Model model) {
        List<ItemResponse> itemResponses = itemService.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());

        model.addAttribute("products", itemResponses);
        return "index";
    }

    @GetMapping("/admin")
    public String redirectAdminPage(Model model) {
        List<ItemResponse> itemResponses = itemService.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());

        model.addAttribute("products", itemResponses);
        return "admin";
    }

    @GetMapping("/settings")
    public String redirectUserSettingsPage(Model model) {
        List<UserResponse> userResponses = userService.findAll()
                .stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());

        model.addAttribute("members", userResponses);
        return "settings";
    }

    @GetMapping("/cart")
    public String redirectCartPage() {
        return "cart";
    }
}
