package cart.catalog.view;

import cart.catalog.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogViewController {

    private final CatalogService catalogService;

    public CatalogViewController(final CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/")
    public String renderProductListPage(final Model model) {
        model.addAttribute("products", this.catalogService.display());
        return "index";
    }
}
