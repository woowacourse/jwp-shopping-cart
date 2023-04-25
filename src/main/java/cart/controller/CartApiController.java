package cart.controller;

import cart.dto.InsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CartApiController {

    private final CartService cartService;

    @Autowired
    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("product")
    public void insertProduct(@RequestBody InsertRequestDto insertRequestDto) {
        cartService.addProduct(insertRequestDto);
    }

    @GetMapping("product")
    public List<ProductResponseDto> getProducts() {
        return cartService.getProducts();
    }
}
