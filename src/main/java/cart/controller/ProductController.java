package cart.controller;

import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final ProductRequest productRequest) {
        final long id = productService.save(productRequest);
        return ResponseEntity.created(URI.create("/admin/" + id)).build(); // TODO 상품 상세페이지
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable final Long id, @RequestBody @Valid final ProductRequest productRequest) {
        productService.update(id, productRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
