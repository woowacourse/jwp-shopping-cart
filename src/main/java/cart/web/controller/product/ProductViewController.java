package cart.web.controller.product;

import cart.domain.product.ProductService;
import cart.domain.product.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductViewController {
    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String loadProductPage(Model model) {
        List<ProductDto> allProducts = productService.getAllProducts();

        model.addAttribute("products", allProducts);

        return "index";
    }
}
