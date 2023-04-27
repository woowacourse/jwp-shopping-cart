package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        productService.add(productRequest.getName(), productRequest.getImage(), productRequest.getPrice());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("id") Integer id,
            @RequestBody @Valid ProductRequest productRequest) {
        Objects.requireNonNull(id);
        productService.update(id, productRequest.getName(), productRequest.getImage(), productRequest.getPrice());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        Objects.requireNonNull(id);
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
