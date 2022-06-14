package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.product.ProductFindResponse;
import woowacourse.shoppingcart.dto.product.ProductSaveRequest;
import woowacourse.shoppingcart.dto.product.ProductSaveResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductSaveResponse> add(@RequestBody ProductSaveRequest request) {
        ProductSaveResponse saved = productService.addProduct(request);
        URI uri = createUri(saved.getProductId());
        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductFindResponse> findProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductFindResponse>> products() {
        return ResponseEntity.ok(productService.findProducts());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable final Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    private URI createUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id)
                .build().toUri();
    }
}
