package cart.controller;

import cart.dto.MemberResponse;
import cart.dto.ProductResponse;
import cart.service.CartService;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    private final ProductService productService;

    private final MemberService memberService;

    private final CartService cartService;

    public PageController(ProductService productService, MemberService memberService, CartService cartService) {
        this.productService = productService;
        this.memberService = memberService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    String allProducts(Model model) {
        List<ProductResponse> productEntities = productService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "index";
    }

    @GetMapping("/admin")
    String adminAllProducts(Model model) {
        List<ProductResponse> productEntities = productService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "admin";
    }

    @GetMapping("/settings")
    String adminAllUsers(Model model) {
        List<MemberResponse> members = memberService.selectAllMember();
        model.addAttribute("members", members);
        return "settings";
    }

    @GetMapping("/cart")
    String cartProducts(Model model) {
        return "cart";
    }
}
