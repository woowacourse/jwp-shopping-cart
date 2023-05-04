package cart.controller;

import cart.dto.AuthInfo;
import cart.dto.InsertRequestDto;
import cart.dto.ProductResponseDto;
import cart.dto.UpdateRequestDto;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.service.CartService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/product")
    public void insertProduct(@RequestBody InsertRequestDto insertRequestDto) {
        validatePrice(insertRequestDto.getPrice());
        cartService.addProduct(insertRequestDto);
    }

    @GetMapping("/product")
    public List<ProductResponseDto> getProducts() {
        return cartService.getProducts();
    }

    @PatchMapping("/product")
    public void updateProduct(@RequestBody UpdateRequestDto updateRequestDto) {
        validatePrice(updateRequestDto.getPrice());
        cartService.updateProduct(updateRequestDto);
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable int id) {
        cartService.deleteProduct(id);
    }

    @GetMapping(value="/cart-products", produces="application/json")
    public List<ProductResponseDto> getCartProduct(HttpServletRequest request) {
        AuthInfo authInfo = new BasicAuthorizationExtractor().extract(request);
        return cartService.getCartItems(authInfo);
    }

    @PostMapping("/cart/{productId}")
    public void addProductToCart(@PathVariable int productId, HttpServletRequest request) {
        AuthInfo authInfo = new BasicAuthorizationExtractor().extract(request);
        cartService.addCartItem(authInfo, productId);
    }

    @DeleteMapping("/cart/{productId}")
    public void deleteProductInCart(@PathVariable int productId, HttpServletRequest request) {
        AuthInfo authInfo = new BasicAuthorizationExtractor().extract(request);
        cartService.deleteCartItem(authInfo, productId);
    }

    private void validatePrice(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 음수일 수 없습니다.");
        }
    }
}
