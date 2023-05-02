package cart.controller.api;

import cart.dto.request.CreateProductRequest;
import cart.dto.request.UpdateProductRequest;
import cart.service.ProductService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> createProduct(@RequestBody @Valid final CreateProductRequest createProductRequest) {
        productService.insert(createProductRequest);
        return ResponseEntity.created(URI.create("/products")).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable("id") final Long id, @RequestBody @Valid final UpdateProductRequest updateProductRequest) {
        productService.update(id, updateProductRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") final Long id) {
        productService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
