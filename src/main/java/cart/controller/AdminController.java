package cart.controller;

import cart.dao.ProductEntity;
import cart.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String getAdminHomePage(Model model) {
        List<ProductEntity> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

}
