package cart.controller;

import cart.domain.product.service.ProductService;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductUpdateRequest;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    public AdminController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> add(@RequestBody final ProductCreateRequest productCreateRequest) {
        productService.create(productCreateRequest);
        return ResponseEntity.created(URI.create("/admin")).build();
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Void> update(@PathVariable final Long id,
        @RequestBody final ProductUpdateRequest productUpdateRequest) {
        productUpdateRequest.setId(id);
        productService.update(productUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
