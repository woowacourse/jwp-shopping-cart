package cart.presentation;

import cart.business.CartService;
import cart.presentation.dto.MemberRequest;
import cart.presentation.dto.MemberResponse;
import cart.presentation.dto.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {
    //field이름으로 판단해서 주입하기도 함
    //interface bean을 어떻게 주입하는가? 어떻게 알고 주입하는가?에 대한 내용임
    // 방법은 여러가지. 1. 우선순위주기 @Primary 2. 이름으로 주입, 3. @Qualifier

    private final CartService cartService;

    public ViewController(CartService productService) {
        this.cartService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductResponse> products = cartService.readProduct().stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> products = cartService.readProduct().stream()
                .map(product -> new ProductResponse(product.getId(),
                        product.getName(),
                        product.getUrl(),
                        product.getPrice()))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        cartService.createMember(new MemberRequest("coding_judith@gmail.com", "judy123"));
        cartService.createMember(new MemberRequest("coding_teo@gmail.com", "teo123"));

        List<MemberResponse> members = cartService.readMembers().stream()
                .map(member -> new MemberResponse(member.getId(),
                        member.getEmail(),
                        member.getPassword()))
                .collect(Collectors.toList());
        model.addAttribute("members", members);
        return "settings";
    }
}
