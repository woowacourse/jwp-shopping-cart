package cart.controller;

import cart.dto.InsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.UpdateRequestDto;
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
    public ResponseEntity<ProductEntity> insertProduct(@RequestBody InsertRequestDto insertRequestDto) {
        validatePrice(insertRequestDto.getPrice());
        final ProductEntity savedProduct = cartService.addProduct(insertRequestDto);
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
    public void updateProduct(@RequestBody UpdateRequestDto updateRequestDto) {
        validatePrice(updateRequestDto.getPrice());
        cartService.updateProduct(updateRequestDto);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable int id) {
        cartService.deleteProduct(id);
    }

    private void validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }
    }
}
