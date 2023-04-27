package cart.web;

import cart.domain.product.CartService;
import cart.domain.product.dto.ProductDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminViewController {
    private final CartService cartService;

    public AdminViewController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String loadAdminPage(Model model) {
        List<ProductDto> allProducts = cartService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "admin";
    }

}
