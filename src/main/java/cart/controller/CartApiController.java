package cart.controller;

import cart.dto.InsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.UpdateRequestDto;
import cart.service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/products")
    public void insertProduct(@RequestBody InsertRequestDto insertRequestDto) {
        validatePrice(insertRequestDto.getPrice());
        cartService.addProduct(insertRequestDto);
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
