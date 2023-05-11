package cart.controller.view;

import cart.controller.dto.MemberResponse;
import cart.controller.dto.ProductResponse;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final ProductService productService;
    private final MemberService memberService;

    public ViewController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String getIndexPage(final Model model) {
        List<ProductResponse> productResponses = productService.findAll();
        model.addAttribute("products", productResponses);
        return "index";
    }

    @GetMapping("/cart")
    public String getCartPage() {
        return "cart";
    }

    @GetMapping("/settings")
    public String getSettingsPage(final Model model) {
        List<MemberResponse> memberResponses = memberService.findAll();
        model.addAttribute("members", memberResponses);
        return "settings";
    }

    @GetMapping("/admin")
    public String getAdminPage(final Model model) {
        List<ProductResponse> productResponses = productService.findAll();
        model.addAttribute("products", productResponses);
        return "admin";
    }
}
