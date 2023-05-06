package cart.web.controller.index;

import cart.domain.product.service.dto.ProductResponseDto;
import cart.domain.product.usecase.FindAllProductsUseCase;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexViewController {
    private final FindAllProductsUseCase findAllProductService;

    public IndexViewController(final FindAllProductsUseCase findAllProductService) {
        this.findAllProductService = findAllProductService;
    }

    @GetMapping
    public String loadIndexPage(final Model model) {
        final List<ProductResponseDto> allProducts = findAllProductService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "index";
    }
}
