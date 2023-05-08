package cart.controller;

import cart.domain.member.dto.MemberDto;
import cart.domain.member.service.MemberService;
import cart.domain.product.dto.ProductDto;
import cart.domain.product.service.ProductService;
import cart.dto.MemberResponse;
import cart.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {

    private final ProductService productService;
    private final MemberService memberService;

    public ViewController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(final Model model) {
        final List<ProductResponse> productResponses = makeResponse(productService.findAll());
        model.addAttribute("products", productResponses);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        final List<ProductResponse> productResponses = makeResponse(productService.findAll());
        model.addAttribute("products", productResponses);
        return "admin";
    }

    @GetMapping("/products")
    @ResponseBody
    public ResponseEntity<List<ProductResponse>> findAll() {
        final List<ProductResponse> productResponses = makeResponse(productService.findAll());
        return ResponseEntity.ok(productResponses);
    }

    private List<ProductResponse> makeResponse(final List<ProductDto> productDtos) {
        return productDtos.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toUnmodifiableList());

    }

    @GetMapping("/settings")
    public String settings(final Model model) {
        final List<MemberDto> memberDtos = memberService.findAll();
        final List<MemberResponse> responses = memberDtos.stream()
            .map(MemberResponse::of)
            .collect(Collectors.toUnmodifiableList());
        model.addAttribute("members", responses);
        return "settings";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
