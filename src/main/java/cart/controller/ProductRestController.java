package cart.controller;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public final class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        final Long productId = productService.addProduct(productRequest);

        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.findProducts();

        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@RequestBody @Valid ProductRequest productRequest,
                                              @PathVariable Long id) {
        productRequest.setId(id);
        productService.updateProduct(productRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
