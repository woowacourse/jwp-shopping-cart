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
    public ResponseEntity<String> createProduct(@RequestBody @Valid ProductSaveRequestDto productSaveRequestDto) {
        cartService.addProduct(productSaveRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> products = cartService.findProducts();

        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/product")
    public ResponseEntity<String> updateProduct(@RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        cartService.updateProduct(productUpdateRequestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        cartService.deleteProduct(id);

        return ResponseEntity.ok().build();
    }
}
