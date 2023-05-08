package cart.controller.ui;

import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final ProductService productService;

    public AdminViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productList(final Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin";
    }
}
