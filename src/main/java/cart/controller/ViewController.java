package cart.controller;

import cart.dto.ResponseMemberDto;
import cart.dto.ResponseProductDto;
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
    public String index(final Model model) {
        final List<ResponseProductDto> responseProductDtos = productService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        final List<ResponseProductDto> responseProductDtos = productService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "admin";
    }

    @GetMapping("/settings")
    public String settings(final Model model) {
        final List<ResponseMemberDto> responseMemberDtos = memberService.findAll();
        model.addAttribute("members", responseMemberDtos);
        return "settings";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
