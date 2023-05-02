package cart;

import cart.member.dto.MemberDto;
import cart.product.dto.ProductResponseDto;
import cart.product.service.ProductService;
import cart.member.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ProductService productService;
    private final MemberService memberService;

    public ViewController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String index(Model model) {
        final List<ProductResponseDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        final List<ProductResponseDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        final List<MemberDto> members = memberService.getMembers();
        model.addAttribute("members", members);
        return "settings";
    }
}
