package cart.catalog.controller;

import cart.catalog.service.ProductCatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductCatalogController {
    
    private final ProductCatalogService productCatalogService;
    
    public ProductCatalogController(final ProductCatalogService productCatalogService) {
        this.productCatalogService = productCatalogService;
    }
    
    @GetMapping("/")
    public String renderProductListPage(final Model model) {
        model.addAttribute("products", this.productCatalogService.display());
        return "index";
    }
    
    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", this.productCatalogService.display());
        return "admin";
    }
}
