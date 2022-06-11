package woowacourse.shoppingcart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.dto.ProductRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.addProduct(productRequest);
        return ResponseEntity.created(URI.create("/products/" + product.getId())).build();
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> findProducts(@RequestParam(required = false) Long size, @RequestParam(required = false) Long page) {
        if (size == null && page == null) {
            return ResponseEntity.ok(productService.findAllProducts());
        }
        return ResponseEntity.ok(productService.findProducts(size, page));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findProduct(@PathVariable final Long productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
