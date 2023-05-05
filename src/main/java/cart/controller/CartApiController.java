package cart.controller;

import cart.dto.AuthInfo;
import cart.dto.ProductResponseDto;
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
}
