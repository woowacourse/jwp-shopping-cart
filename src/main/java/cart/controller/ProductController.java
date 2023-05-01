package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody final ProductRequest productRequest) {
        Long productId = productService.save(productRequest);

        return ResponseEntity
                .created(URI.create("/products/" + productId))
                .build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Integer> modifyProduct(@PathVariable final Long productId, @Valid @RequestBody final ProductRequest productRequest) {
        int updatedCount = productService.update(productId, productRequest);

        return ResponseEntity
                .ok()
                .body(updatedCount);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProduct(@PathVariable final Long productId) {
        productService.delete(productId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
