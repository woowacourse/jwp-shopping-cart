package cart.controller;

import cart.service.MemberService;
import cart.service.ProductService;
import cart.service.dto.MemberDto;
import cart.service.dto.ProductDto;
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
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("products", productDtos);
        return "index";
    }

    @GetMapping("/admin")
    public String viewAdmin(final Model model) {
        final List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("products", productDtos);
        return "admin";
    }

    @GetMapping("/members")
    public String viewUsers(final Model model) {
        final List<MemberDto> memberDtos = memberService.findAll();
        model.addAttribute("members", memberDtos);
        return "members";
    }

    @GetMapping("/carts/me")
    public String viewCarts() {

        return "cart";
    }
}
