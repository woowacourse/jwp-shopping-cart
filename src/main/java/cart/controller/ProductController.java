package cart.controller;

import cart.dto.ProductRequestDto;
import cart.service.ProductService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody final ProductRequestDto productRequestDto) {
        Long id = productService.saveProduct(productRequestDto);
        return ResponseEntity.created(URI.create("products/" + id)).build();
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long id, @Valid @RequestBody ProductRequestDto productRequestDto) {
        productService.updateProduct(id, productRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable final Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(id);
    }
}
