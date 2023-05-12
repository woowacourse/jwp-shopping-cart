package cart.admin.view;

import cart.catalog.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {
    
    private final CatalogService catalogService;
    
    public AdminViewController(final CatalogService catalogService) {
        this.catalogService = catalogService;
    }
    
    @GetMapping("/admin")
    public String renderAdminPage(final Model model) {
        model.addAttribute("products", this.catalogService.display());
        return "admin";
    }
}
