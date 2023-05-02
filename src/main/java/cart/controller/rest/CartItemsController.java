package cart.controller.rest;

import cart.auth.AuthenticationPrincipal;
import cart.domain.Member;
import cart.dto.response.CartItemResponse;
import cart.service.CartService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartItemsController {

    private final CartService cartService;

    public CartItemsController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart-items")
    public List<CartItemResponse> getCartItemsView(@AuthenticationPrincipal Member member, Model model) {
        return cartService.findAllCartItems(member.getMemberId());
    }
}
