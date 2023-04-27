package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        productRepository.save(productRequest.getName(), productRequest.getImage(), productRequest.getPrice());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("id") Integer id,
            @RequestBody @Valid ProductRequest productRequest) {
        Objects.requireNonNull(id);
        productRepository.update(id, productRequest.getName(), productRequest.getImage(), productRequest.getPrice());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        Objects.requireNonNull(id);
        productRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
