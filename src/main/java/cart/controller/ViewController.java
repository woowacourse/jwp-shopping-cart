package cart.controller;

import cart.controller.dto.ItemResponse;
import cart.controller.dto.UserResponse;
import cart.service.ItemService;
import cart.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final ItemService itemService;
    private final UserService userService;

    public ViewController(ItemService itemService, UserService userService) {
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
    public String showUserList(final Model model) {
        List<UserResponse> users = userService.loadAllUser();
        model.addAttribute("members", users);
        return "settings";
    }
}
