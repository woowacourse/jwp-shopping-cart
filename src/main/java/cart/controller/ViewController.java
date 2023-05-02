package cart.controller;

import cart.controller.dto.ItemResponse;
import cart.controller.dto.MemberResponse;
import cart.service.ItemService;
import cart.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {


    private final ItemService itemService;
    private final MemberService memberService;

    public ViewController(ItemService itemService, MemberService memberService) {
        this.itemService = itemService;
        this.memberService = memberService;
    }

    @GetMapping
    public String redirectHomePage(Model model) {
        List<ItemResponse> itemResponses = itemService.findAll();
        model.addAttribute("products", itemResponses);
        return "index";
    }

    @GetMapping("/admin")
    public String redirectAdminPage(Model model) {
        List<ItemResponse> itemResponses = itemService.findAll();
        model.addAttribute("products", itemResponses);
        return "admin";
    }

    @GetMapping("/settings")
    public String redirectSettingPage(Model model) {
        List<MemberResponse> memberResponses = memberService.findAll();
        model.addAttribute("members", memberResponses);
        return "settings";
    }

    @GetMapping("/cart")
    public String redirectCartPage() {
        return "cart";
    }
}
