package cart.product.controller;

import cart.member.mapper.MemberResponseMapper;
import cart.product.mapper.ProductResponseMapper;
import cart.member.service.MemberService;
import cart.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final MemberService memberService;

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
