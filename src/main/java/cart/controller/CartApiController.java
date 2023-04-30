package cart.controller;

import cart.dto.ProductInsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.ProductUpdateResponseDto;
import cart.dto.ProductUpdateRequestDto;
import cart.entity.ProductEntity;
import cart.service.CartService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductEntity> insertProduct(@RequestBody ProductInsertRequestDto productInsertRequestDto) {
        validatePrice(productInsertRequestDto.getPrice());
        final ProductEntity savedProduct = cartService.addProduct(productInsertRequestDto);
        final int savedId = savedProduct.getId();

        return ResponseEntity.created(URI.create("/products/" + savedId)).build();
    }

    @GetMapping("/products/{id}")
    public ProductEntity findProductById(@PathVariable int id) {
        return cartService.findProductById(id);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getProducts() {
        return cartService.getProducts();
    }

    @PutMapping("/products")
    public ResponseEntity<ProductUpdateResponseDto> updateProduct(@RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        validatePrice(productUpdateRequestDto.getPrice());
        final int updatedRowCount = cartService.updateProduct(productUpdateRequestDto);
        final ProductUpdateResponseDto productUpdateResponseDto = new ProductUpdateResponseDto(updatedRowCount);

        return ResponseEntity.ok(productUpdateResponseDto);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductUpdateResponseDto> deleteProduct(@PathVariable int id) {
        final int deletedRowCount = cartService.deleteProduct(id);
        final ProductUpdateResponseDto productUpdateResponseDto = new ProductUpdateResponseDto(deletedRowCount);

        return ResponseEntity.ok(productUpdateResponseDto);
    }

    private void validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }
    }
}
