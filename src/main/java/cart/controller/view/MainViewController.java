package cart.controller.view;

import cart.dto.response.ProductResponseDto;
import cart.service.ProductService;
import java.util.List;
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
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        modelAndView.addObject("products", productResponseDtos);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
