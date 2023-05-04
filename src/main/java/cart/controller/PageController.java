package cart.controller;

import java.util.List;

import cart.dto.MemberDto;
import cart.service.CartService;
import cart.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cart.dto.ProductDto;
import cart.service.ProductService;

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
        List<ProductDto> productEntities = productService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "index";
    }

    @GetMapping("/admin")
    String adminAllProducts(Model model) {
        List<ProductDto> productEntities = productService.selectAllProducts();
        model.addAttribute("products", productEntities);
        return "admin";
    }

    @GetMapping("/settings")
    String adminAllUsers(Model model) {
        List<MemberDto> members = memberService.selectAllMember();
        model.addAttribute("members", members);
        return "settings";
    }

    @GetMapping("/cart")
    String cartProducts(Model model) {
        return "cart";
    }
}
