package cart.controller;

import cart.service.MemberService;
import cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final ProductService productService;
    private final MemberService memberService;

    @GetMapping
    public ModelAndView getIndexPage(final ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView getAdminPage(final ModelAndView modelAndView) {
        modelAndView.setViewName("admin");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

    @GetMapping("/settings")
    public ModelAndView getSettingPage(final ModelAndView modelAndView) {
        modelAndView.setViewName("settings");
        modelAndView.addObject("members", memberService.findAll());
        return modelAndView;
    }

    @GetMapping("/cart")
    public ModelAndView getCartPage(final ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        return modelAndView;
    }
}
