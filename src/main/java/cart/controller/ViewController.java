package cart.controller;

import cart.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ItemService itemService;

    public ViewController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String displayUserItemList(Model model) {
        model.addAttribute("products", itemService.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String displayAdminItemList(Model model) {
        model.addAttribute("products", itemService.findAll());
        return "admin";
    }

    @GetMapping("/settings")
    public String displayUserList(Model model) {
        return "settings";
    }
}
