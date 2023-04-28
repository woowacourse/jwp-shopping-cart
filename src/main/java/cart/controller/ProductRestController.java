package cart.controller;

import cart.dto.ProductRequestDto;
import cart.dto.ProductResponseDto;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public final class ProductRestController {
    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.addProduct(productRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> products = productService.findProducts();

        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/product")
    public ResponseEntity<String> updateProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.updateProduct(productRequestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok().build();
    }
}
