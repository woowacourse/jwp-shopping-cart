package cart.controller.view;

import cart.dto.response.MemberResponse;
import cart.dto.response.ProductResponse;
import cart.service.MembersService;
import cart.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final ProductsService productsService;
    private final MembersService membersService;

    public ViewController(ProductsService productsService, MembersService membersService) {
        this.productsService = productsService;
        this.membersService = membersService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<ProductResponse> productsResponse = productsService.readAll();
        model.addAttribute("products", productsResponse);
        return "index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> productsResponse = productsService.readAll();
        model.addAttribute("products", productsResponse);
        return "admin";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        List<MemberResponse> members = membersService.readAll();
        model.addAttribute("members", members);
        return "settings";
    }
}
