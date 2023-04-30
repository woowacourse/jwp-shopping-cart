package cart.controller;

import cart.service.ItemService;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ItemService itemService;
    private final MemberService memberService;

    public ViewController(final ItemService itemService, final MemberService memberService) {
        this.itemService = itemService;
        this.memberService = memberService;
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
        model.addAttribute("members", memberService.findAll());
        return "settings";
    }

    @GetMapping("/cart")
    public String displayCartList() {
        return "cart";
    }
}
