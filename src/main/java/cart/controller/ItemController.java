package cart.controller;

import cart.domain.Item;
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

    @PostConstruct
    public void initData() {
        Item chicken = new Item("치킨", "https://image.homeplus.kr/td/f42afe4b-e0a8-4c79-a07c-aaee40c93a57", 10000);
        itemService.save(chicken);
    }
}
