package cart.controller;

import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
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

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Void> save(@RequestBody final ProductSaveRequestDto request) {
        productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Void> update(@PathVariable final Long id, @RequestBody ProductUpdateRequestDto request) {
        productService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
