package cart.controller;

import cart.authorization.AuthorizationExtractor;
import cart.authorization.AuthorizationInformation;
import cart.service.CartService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final AuthorizationExtractor<AuthorizationInformation> authorizationExtractor;

    public CartController(CartService cartService, AuthorizationExtractor<AuthorizationInformation> authorizationExtractor) {
        this.cartService = cartService;
        this.authorizationExtractor = authorizationExtractor;
    }

    @GetMapping
    @ModelAttribute
    public String displayCart(HttpServletRequest request, Model model) {
        AuthorizationInformation authorizationInformation = authorizationExtractor.extract(request);
//        model.addAttribute("cart", cartService.putItemIntoCart());
        return "cart";
    }
}
