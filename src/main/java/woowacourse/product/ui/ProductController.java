package woowacourse.product.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import woowacourse.product.application.ProductService;
import woowacourse.product.dto.ProductRequest;
import woowacourse.product.dto.ProductResponse;

@RequestMapping("/api/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody final ProductRequest productRequest) {
        final Long id = productService.addProduct(productRequest);
        return ResponseEntity.created(URI.create("/api/products/" + id)).build();
    }

    // @GetMapping
    // public ResponseEntity<List<Product>> products() {
    //     return ResponseEntity.ok(productService.findProducts());
    // }
    //
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> product(@PathVariable final Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }
    //
    // @DeleteMapping("/{productId}")
    // public ResponseEntity<Void> delete(@PathVariable final Long productId) {
    //     productService.deleteProductById(productId);
    //     return ResponseEntity.noContent().build();
    // }
}
