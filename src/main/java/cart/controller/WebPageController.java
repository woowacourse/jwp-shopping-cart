package cart.controller;

import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

    private final ProductService productService;

    public WebPageController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String renderStartPage(final Model model) {
        model.addAttribute("products", productService.getAll());
        return "index";
    }

    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", productService.getAll());
        return "admin";
    }
}
