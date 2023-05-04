package cart.controller.view;

import cart.auth.AuthenticationPrincipal;
import cart.dto.LoginDto;
import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{id}")
    public String addProduct(@AuthenticationPrincipal LoginDto loginDto, @PathVariable Long id) {
        cartService.addCartItem(id, loginDto.getMemberId());
        return "cart";
    }
}
