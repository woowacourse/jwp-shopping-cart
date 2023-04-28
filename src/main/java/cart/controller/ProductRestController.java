package cart.controller;

import cart.dto.ProductResponseDto;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductRestController {
    private final CartService cartService;

    public ProductRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    public ResponseEntity<Long> createProduct(@RequestBody @Valid ProductSaveRequestDto productSaveRequestDto) {
        Long createdProductId = cartService.addProduct(productSaveRequestDto);

        return ResponseEntity.ok(createdProductId);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> products = cartService.findProducts();

        return ResponseEntity.ok(products);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Long> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        Long updatedProductId = cartService.updateProduct(id, productUpdateRequestDto);

        return ResponseEntity.ok(updatedProductId);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
        Long deletedProductId = cartService.deleteProduct(id);

        return ResponseEntity.ok(deletedProductId);
    }
}
