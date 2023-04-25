package cart.controller;

import cart.dao.ItemDao;
import cart.entity.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ItemController {
    private final ItemDao itemDao;

    public ItemController(final ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @GetMapping("/")
    public String displayItemList(Model model) {
        List<Item> items = itemDao.findAll();
        model.addAttribute("products", items);
        return "index";
    }
}
