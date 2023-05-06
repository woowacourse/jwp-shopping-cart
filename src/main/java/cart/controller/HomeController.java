package cart.controller;

import cart.mapper.MemberResponseMapper;
import cart.mapper.ProductResponseMapper;
import cart.service.MemberService;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final ProductService productService;
    private final MemberService memberService;

    public HomeController(
            final ProductService productService,
            final MemberService memberService
    ) {
        this.productService = productService;
        this.memberService = memberService;
    }

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView) {
        modelAndView.addObject("products", ProductResponseMapper.from(productService.findAll()));
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/settings")
    public ModelAndView settings(ModelAndView modelAndView) {
        modelAndView.addObject("members", MemberResponseMapper.from(memberService.findAll()));
        modelAndView.setViewName("settings");
        return modelAndView;
    }
}
