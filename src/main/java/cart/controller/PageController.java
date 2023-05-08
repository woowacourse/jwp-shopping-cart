package cart.controller;

import cart.controller.dto.MemberResponse;
import cart.controller.dto.ProductResponse;
import cart.service.MemberService;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final ProductService productService;
    private final MemberService memberService;

    public PageController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping
    public String loadHome(Model model) {
        final List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String loadAdmin(Model model) {
        final List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/cart")
    public String loadCart() {
        return "cart";
    }

    @GetMapping("/settings")
    public String loadMembers(Model model) {
        final List<MemberResponse> memberResponses = memberService.findAll();
        model.addAttribute("members", memberResponses);
        return "settings";
    }
}
