package cart.presentation;

import cart.application.ProductCRUDApplication;
import cart.business.MemberReadService;
import cart.business.domain.member.Member;
import cart.presentation.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TemplateController {

    private final ProductCRUDApplication productCRUDApplication;
    private final MemberReadService memberReadService;

    public TemplateController(ProductCRUDApplication productCRUDApplication, MemberReadService memberReadService) {
        this.productCRUDApplication = productCRUDApplication;
        this.memberReadService = memberReadService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> products = productCRUDApplication.readAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductDto> products = productCRUDApplication.readAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        List<Member> members = memberReadService.readAll();
        model.addAttribute("members", members);
        return "settings";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }
}
