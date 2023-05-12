package cart.controller;

import cart.dto.MemberDto;
import cart.dto.ProductDto;
import cart.service.MemberService;
import cart.service.ProductService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ViewController {

    private final ProductService productService;
    private final MemberService memberService;

    public ViewController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String readProducts(final Model model) {
        final List<ProductDto> allProduct = productService.findAllProduct();
        model.addAttribute("products", allProduct);
        return "index";
    }

    @GetMapping(value = {"/admin", "/admin/products"})
    public String readAdmin(final Model model) {
        final List<ProductDto> allProduct = productService.findAllProduct();
        model.addAttribute("products", allProduct);
        return "admin";
    }

    @GetMapping("/settings")
    public String readSettings(final Model model) {
        final List<MemberDto> allMember = memberService.findAllMember();
        model.addAttribute("members", allMember);
        return "settings";
    }

    @GetMapping("/cart")
    public String readCart() {
        return "cart";
    }
}
