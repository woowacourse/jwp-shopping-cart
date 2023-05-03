package cart.presentation;

import cart.business.MemberService;
import cart.business.ProductService;
import cart.business.domain.member.Member;
import cart.presentation.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TemplateController {

    private final ProductService productService;
    private final MemberService memberService;

    public TemplateController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> products = productService.readAll()
                .stream()
                .map(ProductConverter::toProductDto)
                .collect(Collectors.toList());

        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductDto> products = productService.readAll()
                .stream()
                .map(ProductConverter::toProductDto)
                .collect(Collectors.toList());

        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        List<Member> members = memberService.readAll();
        model.addAttribute("members", members);
        return "settings";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
