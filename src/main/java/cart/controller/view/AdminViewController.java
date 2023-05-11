package cart.controller.view;

import cart.dtomapper.ProductResponseDtoMapper;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public final class AdminViewController {

    private final ProductService productService;

    public AdminViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView adminPage(final ModelAndView modelAndView) {
        modelAndView.addObject("products", ProductResponseDtoMapper.asList(productService.findAll()));
        modelAndView.addObject("categories", productService.findCategories());
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}
