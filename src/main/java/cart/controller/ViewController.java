package cart.controller;

import cart.dto.MemberResponse;
import cart.dto.ProductResponse;
import cart.service.MemberFindService;
import cart.service.ProductFindService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private final ProductFindService productFindService;
    private final MemberFindService memberFindService;

    public ViewController(final ProductFindService findService, final MemberFindService memberFindService) {
        this.productFindService = findService;
        this.memberFindService = memberFindService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponse> products = productFindService.findProducts().stream().map(ProductResponse::from).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "index.html";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ProductResponse> products = productFindService.findProducts().stream().map(ProductResponse::from).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "admin.html";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        final List<MemberResponse> members = memberFindService.findAll().stream().map(MemberResponse::from).collect(Collectors.toList());
        model.addAttribute("members", members);
        return "settings.html";
    }
}
