package cart.controller;

import cart.dto.ProductRequest;
import cart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid ProductRequest productRequest) {
        productService.save(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
        productService.update(id, productRequest);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
