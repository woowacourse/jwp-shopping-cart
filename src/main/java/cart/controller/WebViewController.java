package cart.controller;

import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.dto.MembersResponse;
import cart.dto.ProductsResponse;
import cart.service.MemberService;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebViewController {

    private final ProductService productService;
    private final MemberService memberService;

    public WebViewController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping
    public String renderMainPage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", ProductsResponse.of(products));
        return "index";
    }

    @GetMapping("/admin")
    public String renderAdminPage(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", ProductsResponse.of(products));
        return "admin";
    }

    @GetMapping("/cart")
    public String renderCartPage() {
        return "cart";
    }

    @GetMapping("/settings")
    public String renderSettingPage(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", MembersResponse.of(members));
        return "settings";
    }
}
