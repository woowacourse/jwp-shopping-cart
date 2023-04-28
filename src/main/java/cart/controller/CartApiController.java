package cart.controller;

import cart.dto.InsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.UpdateRequestDto;
import cart.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("product")
    public void insertProduct(@RequestBody InsertRequestDto insertRequestDto) {
        validatePrice(insertRequestDto.getPrice());
        cartService.addProduct(insertRequestDto);
    }

    @GetMapping("product")
    public List<ProductResponseDto> getProducts() {
        return cartService.getProducts();
    }

    @PutMapping("product")
    public void updateProduct(@RequestBody UpdateRequestDto updateRequestDto) {
        validatePrice(updateRequestDto.getPrice());
        cartService.updateProduct(updateRequestDto);
    }

    @DeleteMapping("product/{id}")
    public void deleteProduct(@PathVariable int id) {
        cartService.deleteProduct(id);
    }

    private void validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }
    }
}
