package cart.controller;


import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    private final ProductService productService;
    private final MemberService memberService;

    public CartController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String showProductsList(Model model) {
        model.addAttribute("products", productService.findProducts());
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {
        model.addAttribute("products", productService.findProducts());
        return "admin";
    }

    @GetMapping("/settings")
    public String showSettings(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "settings";
    }

    @GetMapping("/cart")
    public String showCart() {
        return "cart";
    }
}
