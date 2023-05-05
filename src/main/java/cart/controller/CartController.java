package cart.controller;

import cart.service.MemberService;
import cart.service.ProductService;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {

    private final ProductService productService;
    private final MemberService memberService;

    public CartController(final ProductService productService, final MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping(path = "/")
    public ModelAndView home() {
        return new ModelAndView("index", Map.of(
                "products", productService.findAll()
        ));
    }

    @GetMapping(path = "/admin")
    public ModelAndView admin() {
        return new ModelAndView("admin", Map.of(
                "products", productService.findAll()
        ));
    }

    @GetMapping(path = "/settings")
    public ModelAndView settings() {
        return new ModelAndView("settings", Map.of(
                "members", memberService.findAll()
        ));
    }
}
