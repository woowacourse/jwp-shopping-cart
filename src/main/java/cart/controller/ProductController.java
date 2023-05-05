package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        int id = productService.addProduct(productRequest);
        return ResponseEntity.created(URI.create("/products/" + id)).build();
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductRequest productRequest,
        @PathVariable int id) {
        productService.updateProduct(productRequest, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
