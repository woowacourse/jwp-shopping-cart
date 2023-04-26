package cart.controller;

import cart.dto.ItemRequest;
import cart.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ItemService itemService;

    public AdminController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String displayItemList(Model model) {
        model.addAttribute("products", itemService.findAll());
        return "admin";
    }

    @PostMapping("/items/add")
    public String addItem(@RequestBody ItemRequest itemRequest) {
        itemService.save(itemRequest);
        return "redirect:/admin";
    }

    @PostMapping("/items/edit/{itemId}")
    public String editItem(@PathVariable Long itemId, @RequestBody ItemRequest itemRequest) {
        itemService.updateItem(itemId, itemRequest);
        return "redirect:/admin";
    }

    @PostMapping("/items/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
        return "redirect:/admin";
    }
}
