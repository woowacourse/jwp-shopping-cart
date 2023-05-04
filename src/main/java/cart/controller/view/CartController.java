package cart.controller.view;

import cart.service.CartService;
import cart.service.MembersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final MembersService membersService;

    public CartController(CartService cartService, MembersService membersService) {
        this.cartService = cartService;
        this.membersService = membersService;
    }

    @GetMapping
    public String cart() {
        return "cart";
    }
}
