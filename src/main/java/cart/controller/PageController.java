package cart.controller;

import cart.dto.MemberResponse;
import cart.dto.ProductResponse;
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

    @GetMapping("/")
    public String productPageView(Model model) {
        List<ProductResponse> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String adminPageView(Model model) {
        List<ProductResponse> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String settingPageView(Model model) {
        List<MemberResponse> members = memberService.findAllMembers();
        model.addAttribute("members", members);
        return "settings";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

}
