package woowacourse.shoppingcart.product.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.product.application.ProductService;
import woowacourse.shoppingcart.product.domain.Product;
import woowacourse.shoppingcart.product.dto.ProductsResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> products() {
        final List<Product> products = productService.findProducts();
        final ProductsResponse response = ProductsResponse.from(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> product(@PathVariable final Long productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }
}
