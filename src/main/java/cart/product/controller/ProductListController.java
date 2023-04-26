package cart.product.controller;

import cart.product.service.ProductListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductListController {
    
    private final ProductListService productListService;
    
    public ProductListController(final ProductListService productListService) {
        this.productListService = productListService;
    }
    
    @GetMapping("/")
    public String renderProductListPage(final Model model) {
        model.addAttribute("products", this.productListService.display());
        return "index";
    }
    
    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", this.productListService.display());
        return "admin";
    }
}
