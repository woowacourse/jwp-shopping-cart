package cart.controller;

import cart.authorization.AuthorizationExtractor;
import cart.authorization.AuthorizationInformation;
import cart.dto.ItemResponse;
import cart.service.CartService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;
    private final AuthorizationExtractor<AuthorizationInformation> authorizationExtractor;

    public CartController(CartService cartService, AuthorizationExtractor<AuthorizationInformation> authorizationExtractor) {
        this.cartService = cartService;
        this.authorizationExtractor = authorizationExtractor;
    }

    @GetMapping("/cart")
    @ModelAttribute
    public String displayCartMainPage() {
        return "cart";
    }

    @GetMapping("/carts")
    public List<ItemResponse> displayCart(@RequestHeader(value = "Authorization") String authorization) {
        AuthorizationInformation authorizationInformation = authorizationExtractor.extract(authorization);
        return cartService.findAllItemByAuthInfo(authorizationInformation);
    }

    @GetMapping("/carts/new/{itemId}")
    public String addItemIntoCart(@RequestHeader(value = "Authorization") String authorization, @PathVariable Long itemId) {
        AuthorizationInformation authorizationInformation = authorizationExtractor.extract(authorization);
        cartService.putItemIntoCart(itemId, authorizationInformation);
        return "ok";
    }
}
