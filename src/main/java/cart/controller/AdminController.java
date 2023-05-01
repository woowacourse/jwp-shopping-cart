package cart.controller;

import cart.controller.dto.ProductDto;
import cart.domain.ProductCategory;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public ModelAndView getProducts() {
        final List<ProductDto> products = productService.getProducts();
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("admin");
        mv.addObject("products", products);
        return mv;
    }

    @ModelAttribute("categories")
    public List<ProductCategory> productCategories() {
        return List.of(ProductCategory.values());
    }
}
