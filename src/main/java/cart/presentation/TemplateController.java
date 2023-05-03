package cart.presentation;

import cart.business.MemberReadService;
import cart.business.ProductCRUDService;
import cart.business.domain.member.Member;
import cart.presentation.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TemplateController {

    private final ProductCRUDService productCRUDService;
    private final MemberReadService memberReadService;

    public TemplateController(ProductCRUDService productCRUDService, MemberReadService memberReadService) {
        this.productCRUDService = productCRUDService;
        this.memberReadService = memberReadService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> products = productCRUDService.readAll()
                .stream()
                .map(ProductConverter::toProductDto)
                .collect(Collectors.toList());

        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductDto> products = productCRUDService.readAll()
                .stream()
                .map(ProductConverter::toProductDto)
                .collect(Collectors.toList());

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
