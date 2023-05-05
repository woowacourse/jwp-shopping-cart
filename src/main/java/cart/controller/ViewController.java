package cart.controller;

import cart.dto.MemberResponse;
import cart.dto.ProductResponse;
import cart.service.member.MemberFindService;
import cart.service.product.ProductFindService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView index() {
        List<ProductResponse> products = productFindService.findProducts().stream().map(ProductResponse::from).collect(Collectors.toList());
        final ModelAndView modelAndView = new ModelAndView("index.html");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin() {
        List<ProductResponse> products = productFindService.findProducts().stream().map(ProductResponse::from).collect(Collectors.toList());
        final ModelAndView modelAndView = new ModelAndView("admin.html");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/settings")
    public ModelAndView settings() {
        final List<MemberResponse> members = memberFindService.findAll().stream().map(MemberResponse::from).collect(Collectors.toList());
        final ModelAndView modelAndView = new ModelAndView("settings.html");
        modelAndView.addObject("members", members);
        return modelAndView;
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart.html";
    }
}
