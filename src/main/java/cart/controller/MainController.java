package cart.controller;

import cart.dto.response.MemberResponse;
import cart.dto.response.ProductResponse;
import cart.service.MemberService;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/")
public class MainController {
    private final ProductService productService;
    private final MemberService memberService;

    public MainController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping()
    public String home(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("admin")
    public String productList(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("settings")
    public String memberList(Model model) {
        List<MemberResponse> members = memberService.findAll();
        model.addAttribute("members", members);
        return "settings";
    }
}
