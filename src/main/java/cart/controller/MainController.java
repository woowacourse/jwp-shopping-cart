package cart.controller;

import cart.domain.Member;
import cart.dto.response.ProductResponse;
import cart.service.MemberService;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private final ProductService productService;
    private final MemberService memberService;

    public MainController(ProductService productService, MemberService memberService) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView mav) {
        List<ProductResponse> products = productService.findAll();
        mav.addObject("products", products);
        mav.setViewName("index");
        return mav;
    }

    @GetMapping("/admin")
    public ModelAndView admin(ModelAndView mav) {
        List<ProductResponse> products = productService.findAll();
        mav.addObject("products", products);
        mav.setViewName("admin");
        return mav;
    }

    @GetMapping("/settings")
    public ModelAndView settings(ModelAndView mav) {
        List<Member> members = memberService.findAll();
        mav.addObject("members", members);
        mav.setViewName("settings");
        return mav;
    }

    @GetMapping("/myCart")
    public ModelAndView cart(ModelAndView mav) {
        mav.setViewName("myCart");
        return mav;
    }
}
