package cart.web.controller.index;

import cart.domain.product.service.dto.ProductResponseDto;
import cart.domain.product.usecase.FindAllProductsUseCase;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminViewController {
    private final FindAllProductsUseCase findAllProductService;

    public AdminViewController(final FindAllProductsUseCase findAllProductService) {
        this.findAllProductService = findAllProductService;
    }

    @GetMapping
    public String loadAdminPage(final Model model) {
        final List<ProductResponseDto> allProducts = findAllProductService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "admin";
    }
}
