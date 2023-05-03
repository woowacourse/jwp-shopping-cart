package cart.controller;

import cart.service.MemberService;
import cart.service.ProductService;
import cart.service.dto.member.MemberSearchResponse;
import cart.service.dto.product.ProductSearchResponse;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ProductService productService;
    private final MemberService memberService;

    public ViewController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String showWelcomePage(final Model model) {
        List<ProductSearchResponse> productSearchResponses = productService.searchAllProducts();
        model.addAttribute("products", productSearchResponses);
        return "index";
    }

    @GetMapping("/admin")
    public String searchProduct(final Model model) {
        List<ProductSearchResponse> productSearchResponses = productService.searchAllProducts();
        model.addAttribute("products", productSearchResponses);
        return "admin";
    }

    @GetMapping("/settings")
    public String showAllMembers(final Model model) {
        List<MemberSearchResponse> memberSearchResponses = memberService.searchAllMembers();
        model.addAttribute("members", memberSearchResponses);
        return "settings";
    }
}
