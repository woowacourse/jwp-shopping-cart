package cart.controller;

import cart.dto.response.CategoryResponseDto;
import cart.dto.response.ProductResponseDto;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView indexPage(final ModelAndView modelAndView) {
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        modelAndView.addObject("products", productResponseDtos);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView adminPage(final ModelAndView modelAndView) {
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        final List<CategoryResponseDto> categories = productService.findCategories();
        modelAndView.addObject("products", productResponseDtos);
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}
