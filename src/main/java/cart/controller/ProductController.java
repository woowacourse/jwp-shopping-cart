package cart.controller;

import cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    
    @GetMapping("/")
    public ModelAndView findAll(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("products", productService.findAll());
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }
}
