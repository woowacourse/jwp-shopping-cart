package cart.controller;

import cart.dto.ItemRequest;
import cart.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;

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
