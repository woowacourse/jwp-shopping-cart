package cart.controller;

import cart.authorization.BasicAuthorization;
import cart.controller.dto.ProductResponseDto;
import cart.service.CartService;
import cart.service.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
public class CartApiController {
    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductResponseDto>> displayProduct(@BasicAuthorization UserDto userDto){
        List<ProductResponseDto> carts = cartService.displayUserCart(userDto.getEmail())
                .stream().map(productDto ->
                        new ProductResponseDto(
                                productDto.getId(),
                                productDto.getName(),
                                productDto.getPrice(),
                                productDto.getImage()
                        ))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(carts);
    }

    @PostMapping("/{productId}")
    public void addCart(@PathVariable Long productId, @BasicAuthorization UserDto userDto){
        System.out.println(userDto.getId());
        cartService.addCart(userDto.getId(),productId);
    }

    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Long cartId){
        cartService.deleteCart(cartId);
    }
}
