package cart.web.controller.product;

import cart.domain.product.ProductService;
import cart.web.controller.product.dto.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final Long productId = productService.save(productRequest);
        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long productId,
                                              @RequestBody @Valid final ProductRequest productRequest) {
        productService.update(productId, productRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
}
