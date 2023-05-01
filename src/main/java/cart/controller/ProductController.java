package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductRepository;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ProductRequest product) {
        productRepository.add(product);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,
        @Valid @RequestBody ProductRequest product) {
        productRepository.update(id, product);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        productRepository.remove(id);
        return ResponseEntity.ok().build();
    }
}
