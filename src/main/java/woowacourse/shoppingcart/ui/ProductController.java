package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<Void> handleNotFoundProductException() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/products")
    public ResponseEntity<ProductsResponse> getProducts() {
        List<Product> products = productService.findProducts();
        return ResponseEntity.ok(new ProductsResponse(products));
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        Product product = productService.findProductById(productId);
        return ResponseEntity.ok(new ProductResponse(product));
    }
}
