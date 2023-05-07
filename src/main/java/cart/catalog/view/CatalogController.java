package cart.catalog.view;

import cart.catalog.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(final CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/")
    public String renderProductListPage(final Model model) {
        model.addAttribute("products", this.catalogService.display());
        return "index";
    }
}
