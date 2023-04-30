package cart.controller;

import cart.dto.ProductDto;
import cart.dto.request.ProductSaveRequest;
import cart.dto.request.ProductUpdateRequest;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        List<ProductDto> products = productService.findAllProducts();
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/products")
    public ResponseEntity<String> saveProduct(@RequestBody ProductSaveRequest productSaveRequest) {
        productService.saveProduct(productSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable long productId, @RequestBody ProductUpdateRequest updateRequest) {
        productService.updateProduct(productId, updateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
