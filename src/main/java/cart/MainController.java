package cart;

import cart.member.service.MemberService;
import cart.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final ProductService productService;
    private final MemberService memberService;

    @Autowired
    public MainController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping(path = "/")
    public String home(Model model) {
        model.addAttribute("products", productService.getAllProducts());

        return "index";
    }

    @GetMapping(path = "/admin")
    public String admin(Model model) {
        model.addAttribute("products", productService.getAllProducts());

        return "admin";
    }

    @GetMapping(path = "/settings")
    public String settings(Model model) {
        model.addAttribute("members", memberService.getAllMembers());

        return "settings";
    }
}
