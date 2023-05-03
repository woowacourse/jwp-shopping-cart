package cart.controller;

import cart.dto.member.MemberResponse;
import cart.dto.product.ProductResponse;
import cart.service.member.MemberService;
import cart.service.product.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final ProductService productService;
    private final MemberService memberService;

    public PageController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String main(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String setting(Model model) {
        List<MemberResponse> members = memberService.findAll();
        model.addAttribute("members", members);
        return "settings";
    }
}
