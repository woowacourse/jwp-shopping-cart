package cart.controller;

import cart.dto.response.ResponseMemberDto;
import cart.dto.response.ResponseProductDto;
import cart.service.CartService;
import cart.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final CartService cartService;
    private final MemberService memberService;

    @Autowired
    public ViewController(final CartService cartService, MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String readProducts(final Model model) {
        final List<ResponseProductDto> responseProductDtos = cartService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "index";
    }

    @GetMapping("/settings")
    public String getMembers(final Model model) {
        final List<ResponseMemberDto> responseMemberDtos = memberService.findAll();
        model.addAttribute("members", responseMemberDtos);
        return "settings";
    }

    @GetMapping("/admin")
    public String readAdminProducts(final Model model) {
        final List<ResponseProductDto> responseProductDtos = cartService.findAll();
        model.addAttribute("products", responseProductDtos);
        return "admin";
    }
}
