package cart.controller;


import cart.authentication.AuthenticationPrincipal;
import cart.domain.CartEntity;
import cart.dto.AuthInfo;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    private final CartService cartService;

    public CartApiController(CartService cartService) {
        this.cartService = cartService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/items/{productId}")
    public String addItem(@PathVariable int productId, @AuthenticationPrincipal AuthInfo authInfo) {
        cartService.addItem(authInfo, productId);
        return "ok";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/items")
    public List<CartEntity> itemList(@AuthenticationPrincipal AuthInfo authInfo) {
        List<CartEntity> cartEntities = cartService.searchItems(authInfo);
        return cartEntities;
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{cartId}")
    public void deleteCartItem(@PathVariable int cartId) {
        cartService.deleteItem(cartId);
    }

}
