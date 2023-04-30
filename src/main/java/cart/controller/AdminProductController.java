package cart.controller;

import cart.dto.ProductSaveRequest;
import cart.dto.ProductUpdateRequest;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> saveProduct(@Valid @RequestBody final ProductSaveRequest productSaveRequest) {
        final Long id = productService.save(productSaveRequest);
        return ResponseEntity.created(URI.create("/admin/product/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable final Long id,
            @Valid @RequestBody final ProductUpdateRequest productUpdateRequest) {
        productService.update(id, productUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
