package cart.controller;

import cart.domain.Member;
import cart.service.ProductService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {

    private final ProductService productService;

    public CartController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/")
    public ModelAndView home() {
        return new ModelAndView("index", Map.of(
                "products", productService.findAll()
        ));
    }

    @GetMapping(path = "/admin")
    public ModelAndView admin() {
        return new ModelAndView("admin", Map.of(
                "products", productService.findAll()
        ));
    }

    @GetMapping(path = "/settings")
    public ModelAndView settings() {
        return new ModelAndView("settings", Map.of(
                "members", List.of(
                        new Member(1L, "dummy@gmail.com", "1234"),
                        new Member(2L, "dummy2@gmail.com", "4567")
                )
        ));
    }
}
