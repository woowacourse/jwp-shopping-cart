package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final ProductRequest request) {
        productService.save(request.getName(), request.getPrice(), request.getImage());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable @NotNull final Long id,
            @RequestBody @Valid final ProductRequest request) {
        productService.update(id, request.getName(), request.getPrice(), request.getImage());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @NotNull final Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
