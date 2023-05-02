package cart.controller;

import cart.dto.CartResponse;
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
public class AdminController {

    private final ProductService productService;
    private final MemberService memberService;
    private final CartService cartService;

    public AdminController(ProductService productService, MemberService memberService, CartService cartService) {
        this.productService = productService;
        this.memberService = memberService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String productList(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<ProductResponse> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String memberPage(Model model) {
        List<MemberResponse> members = memberService.findAll();
        model.addAttribute("members", members);
        return "settings";
    }

    @GetMapping("/cart")
    public String cartPage(Model model) {
        List<CartResponse> carts = cartService.findAll();
        model.addAttribute("carts", carts);
        return "cart";
    }
}
