package cart.controller.admin;

import cart.controller.dto.ProductDto;
import cart.persistence.entity.ProductCategory;
import cart.service.ShoppingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ShoppingService shoppingService;

    public AdminController(final ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @GetMapping
    public String getProducts(final Model model) {
        final List<ProductDto> products = shoppingService.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @ModelAttribute("categorys")
    public List<ProductCategory> productCategories() {
        return List.of(ProductCategory.values());
    }
}
