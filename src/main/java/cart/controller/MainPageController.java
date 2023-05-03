package cart.controller;

import cart.controller.dto.ItemResponse;
import cart.controller.dto.LoginUser;
import cart.controller.dto.UserResponse;
import cart.service.CartService;
import cart.service.ItemService;
import cart.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainPageController {

    private final ItemService itemService;
    private final UserService userService;

    public MainPageController(final ItemService itemService, final UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showItemList(final Model model) {
        List<ItemResponse> items = itemService.loadAllItem();
        model.addAttribute("products", items);
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(final Model model) {
        List<ItemResponse> items = itemService.loadAllItem();
        model.addAttribute("products", items);
        return "admin";
    }

    @GetMapping("/settings")
    public String showSetting(final Model model) {
        List<UserResponse> users = userService.loadAllUser();
        model.addAttribute("users", users);
        return "settings";
    }

    @GetMapping("/cart")
    public String showCart(final Model model) {
        return "cart";
    }
}
