package cart.controller;

import cart.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String displayItemList(Model model) {
        model.addAttribute("products", itemService.findAll());
        return "index";
    }
}
