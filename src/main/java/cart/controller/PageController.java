package cart.controller;

import cart.dto.ProductDto;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    private final ProductService productService;
    private final MemberService memberService;

    public PageController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "index";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }

    @GetMapping("/settings")
    public String settings(final Model model) {
        model.addAttribute("members", memberService.findAll());
        return "settings";
    }

    @GetMapping("/products/{id}")
    public String productDetail(@PathVariable final Long id, final Model model) {
        final ProductDto result = productService.findById(id);
        model.addAttribute("product", result);
        return "product";
    }
}
