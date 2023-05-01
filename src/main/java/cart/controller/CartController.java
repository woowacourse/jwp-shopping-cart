package cart.controller;

import cart.dto.MemberDto;
import cart.dto.ProductResponseDto;
import cart.service.CartService;
import cart.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;

    public CartController(CartService cartService, MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String index(Model model) {
        final List<ProductResponseDto> products = cartService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        final List<ProductResponseDto> products = cartService.getProducts();
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
