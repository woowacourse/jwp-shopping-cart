package cart.controller;

import cart.domain.product.Product;
import cart.dto.ProductRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final ProductRequest request) {
        final Long id = productService.save(request.getName(), request.getPrice(), request.getImage());
        return ResponseEntity.created(URI.create("/product/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> showProducts() {
        return ResponseEntity.ok(List.of());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable @NotNull final Long id,
            @RequestBody @Valid final ProductRequest request) {
        final Long updatedId = productService.update(id, request.getName(), request.getPrice(), request.getImage());
        return ResponseEntity.created(URI.create("/product/" + updatedId)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @NotNull final Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}
