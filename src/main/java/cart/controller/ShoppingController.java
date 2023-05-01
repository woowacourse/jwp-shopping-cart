package cart.controller;

import cart.controller.dto.ProductDto;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingController {

    private final ProductService productService;

    public ShoppingController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        final List<ProductDto> products = productService.getProducts();
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        mv.addObject("products", products);
        return mv;
    }

    @GetMapping("/{productId}")
    public ModelAndView getProduct(@PathVariable Long productId) {
        final ProductDto productDto = productService.getById(productId);
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("product");
        mv.addObject("product", productDto);
        return mv;
    }
}
