package cart.controller;

import cart.dto.AuthInfo;
import cart.dto.MemberDto;
import cart.dto.ProductDto;
import cart.service.CartService;
import cart.service.MemberService;
import cart.service.ProductService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {
    private final ProductService productService;
    private final MemberService memberService;

    public ViewController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping()
    public String home(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("admin")
    public String productList(Model model) {
        List<ProductDto> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @RequestMapping("settings")
    public String userSetting(Model model) {
        List<MemberDto> members = memberService.findAll();
        model.addAttribute("members", members);
        return "settings";
    }

    @RequestMapping("cart")
    public String cartList() {
        return "cart";
    }
}
