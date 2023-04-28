package cart.controller;

import cart.controller.dto.ItemResponse;
import cart.service.ItemService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ItemService itemService;

    public ViewController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String redirectHomePage(Model model) {
        List<ItemResponse> itemResponses = itemService.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());

        model.addAttribute("products", itemResponses);
        return "index";
    }

    @GetMapping("/admin")
    public String redirectAdminPage(Model model) {
        List<ItemResponse> itemResponses = itemService.findAll()
                .stream()
                .map(ItemResponse::from)
                .collect(Collectors.toList());

        model.addAttribute("products", itemResponses);
        return "admin";
    }
}
