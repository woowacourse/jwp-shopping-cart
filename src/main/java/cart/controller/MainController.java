package cart.controller;

import cart.dto.response.ProductResponse;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
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
}
