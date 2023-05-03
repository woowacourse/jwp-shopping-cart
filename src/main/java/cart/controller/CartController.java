package cart.controller;

import cart.authentication.AuthenticatedMember;
import cart.dto.AuthInfo;
import cart.dto.CartItemResponseDto;
import cart.service.CartService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("")
    public List<CartItemResponseDto> cartItemList(@AuthenticatedMember AuthInfo authInfo) {
        return cartService.findAll(authInfo.getEmail());
    }

}
