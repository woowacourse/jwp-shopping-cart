package cart.controller.view;

import cart.dtomapper.ProductResponseDtoMapper;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class MainViewController {

    private final ProductService productService;

    public MainViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView indexPage(final ModelAndView modelAndView) {
        modelAndView.addObject("products", ProductResponseDtoMapper.asList(productService.findAll()));
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
