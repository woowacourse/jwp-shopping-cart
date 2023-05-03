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
    public String index(Model model) {
        List<ProductResponse> products = productService.findProducts();
        model.addAttribute("products", products);
        return "index.html";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> products = productService.findProducts();
        model.addAttribute("products", products);
        return "admin.html";
    }

    @GetMapping("/settings")
    public String setting(Model model) {
        List<MemberResponse> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "settings.html";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart.html";
    }
}
