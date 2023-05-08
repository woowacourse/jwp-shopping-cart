package cart.controller;

import cart.authorization.BasicAuthorizationExtractor;
import cart.config.Auth;
import cart.dto.AuthorizationInformation;
import cart.dto.ItemResponse;
import cart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;
    private final BasicAuthorizationExtractor basicAuthorizationExtractor;

    public CartController(CartService cartService, BasicAuthorizationExtractor basicAuthorizationExtractor) {
        this.cartService = cartService;
        this.basicAuthorizationExtractor = basicAuthorizationExtractor;
    }

    @GetMapping("/cart")
    @ModelAttribute
    public String displayCartMainPage() {
        return "cart";
    }

    @GetMapping("/carts")
    public List<ItemResponse> displayCart(@Auth AuthorizationInformation authorizationInformation) {
        return cartService.findAllItemByAuthInfo(authorizationInformation);
    }

    @PostMapping("/carts/new/{itemId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String addItem(@Auth AuthorizationInformation authorizationInformation, @PathVariable Long itemId) {
        cartService.putItemIntoCart(itemId,  authorizationInformation);
        return "ok";
    }

    @PostMapping("/carts/delete/{itemId}")
    public String deleteItem(@Auth AuthorizationInformation authorizationInformation, @PathVariable Long itemId) {
        cartService.deleteItemFromCart(itemId, authorizationInformation);
        return "ok";
    }
}
