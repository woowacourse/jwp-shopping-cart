package cart.controller;

import java.util.List;

import cart.dto.MemberDto;
import cart.entity.Member;
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

    public PageController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
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
}
