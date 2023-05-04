package cart.controller;

import cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class PageController {
    private final ProductService productService;

    @GetMapping
    public ModelAndView getIndexPage(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView getAdminPage(ModelAndView modelAndView) {
        modelAndView.setViewName("admin");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }
}
