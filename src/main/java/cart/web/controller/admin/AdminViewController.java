package cart.web.controller.admin;

import cart.domain.product.ProductCategory;
import cart.domain.product.ProductService;
import cart.web.controller.product.dto.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminViewController {

    private final ProductService productService;

    public AdminViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String renderAdmin(final Model model) {
        final List<ProductResponse> productResponses = productService.getProducts()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getProductNameValue(),
                        product.getImageUrlValue(), product.getPriceValue(), product.getCategory()))
                .collect(Collectors.toList());
        model.addAttribute("products", productResponses);
        return "admin.html";
    }

    @ModelAttribute("categorys")
    public List<ProductCategory> renderCategories() {
        return List.of(ProductCategory.values());
    }
}
