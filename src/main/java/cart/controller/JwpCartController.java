package cart.controller;

import cart.dto.member.MemberDto;
import cart.dto.member.MemberResponseDto;
import cart.dto.product.ProductDto;
import cart.dto.product.ProductResponseDto;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class JwpCartController {

    private final ProductService productService;
    private final MemberService memberService;

    public JwpCartController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductDto> productDtos = productService.findAll();
        List<ProductResponseDto> response = productDtos.stream()
                .map(ProductResponseDto::fromDto)
                .collect(toList());
        model.addAttribute("products", response);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductDto> productDtos = productService.findAll();
        List<ProductResponseDto> response = productDtos.stream()
                .map(ProductResponseDto::fromDto)
                .collect(toList());
        model.addAttribute("products", response);
        return "admin";
    }

    @GetMapping("/settings")
    public String setting(Model model) {
        List<MemberDto> memberDtos = memberService.findAll();
        List<MemberResponseDto> response = memberDtos.stream()
                .map(MemberResponseDto::fromDto)
                .collect(toList());
        model.addAttribute("members", response);
        return "settings";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
