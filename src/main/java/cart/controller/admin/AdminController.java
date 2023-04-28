package cart.controller.admin;

import cart.controller.dto.ProductDto;
import cart.persistence.entity.ProductCategory;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(final Model model) {
        final List<ProductDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    // TODO: 관리자의 권한으로 접근하면, 소비자가 접근할 때와 다른 화면을 보여줄 필요가 있지 않을까
    @GetMapping("products/{productId}")
    public String getProduct(@PathVariable Long productId, final Model model) {
        final ProductDto productDto = productService.getById(productId);
        model.addAttribute("product", productDto);
        return "product";
    }

    @ModelAttribute("categorys")
    public List<ProductCategory> productCategories() {
        return List.of(ProductCategory.values());
    }
}
