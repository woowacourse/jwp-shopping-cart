package cart.web.controller.product;

import cart.domain.product.Product;
import cart.domain.product.ProductService;
import cart.web.controller.product.dto.ProductResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductViewController {
    private final ProductService productService;

    public ProductViewController(final ProductService productService) {
        this.productService = productService;
    }

    // TODO: 관리자의 권한으로 접근하면, 소비자가 접근할 때와 다른 화면을 보여줄 필요가 있지 않을까
    @GetMapping("/products/{productId}")
    public String renderProduct(@PathVariable Long productId, final Model model) {
        final Product product = productService.getById(productId);
        final ProductResponse productResponse = new ProductResponse(product.getId(), product.getProductNameValue(),
                product.getImageUrlValue(), product.getPriceValue(), product.getCategory());
        model.addAttribute("product", productResponse);
        return "product.html";
    }
}
