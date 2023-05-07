package cart.controller;

import cart.domain.member.dto.MemberResponse;
import cart.domain.member.service.MemberService;
import cart.domain.product.dto.ProductResponse;
import cart.domain.product.service.ProductService;
import java.util.List;
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
        final List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        final List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String settings(final Model model) {
        final List<MemberResponse> responses = memberService.findAll();
        model.addAttribute("members", responses);
        return "settings";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/products")
    @ResponseBody
    public ResponseEntity<List<ProductResponse>> findAll() {
        final List<ProductResponse> productResponses = productService.findAll();
        return ResponseEntity.ok(productResponses);
    }
}
