package cart.controller;

import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView admin(ModelAndView modelAndView) {
        modelAndView.addObject("products", productService.findAll());
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}
