package cart.controller;

import cart.dao.ItemDao;
import cart.entity.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ItemDao itemDao;

    public AdminController(final ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @GetMapping
    public String displayItemList(Model model) {
        List<Item> items = itemDao.findAll();
        model.addAttribute("products", items);
        return "admin";
    }
}
