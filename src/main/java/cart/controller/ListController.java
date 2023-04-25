package cart.controller;

import cart.dao.entity.Item;
import cart.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListController {

    private final ItemService itemService;

    @Autowired
    public ListController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String showItemList(final Model model) {
        List<Item> items = itemService.loadAllItem();
        model.addAttribute("products", items);
        return "index";
    }
}
