package cart.controller.view;

import cart.auth.AuthenticationPrincipal;
import cart.domain.Cart;
import cart.domain.CartRepository;
import cart.domain.Member;
import cart.dto.response.CartItemResponse;
import cart.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getCartView() {
        return "cart";
    }

    @PostMapping("/{id}")
    public String addProduct(@AuthenticationPrincipal Member member, @PathVariable Long id) {
        cartService.addCartItem(id, member.getMemberId());
        return "cart";
    }
}
