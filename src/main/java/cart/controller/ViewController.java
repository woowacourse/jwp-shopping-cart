package cart.controller;

import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.service.MemberService;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Transactional
@Controller
public class ViewController {

    private final ProductService productService;
    private final MemberService memberService;

    public ViewController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping
    public String viewHome(final Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String viewAdmin(final Model model) {
        final List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/members")
    public String viewUsers(final Model model) {
        final List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    @GetMapping("/carts/me")
    public String viewCarts() {
        return "cart";
    }
}
