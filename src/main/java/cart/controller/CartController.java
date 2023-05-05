package cart.controller;

import cart.auth.AuthInfo;
import cart.auth.BasicAuthorizationExtractor;
import cart.domain.cart.CartItem;
import cart.service.CartService;
import cart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    public CartController(final CartService cartService, final UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public String getSettingsPage() {
        return "cart";
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> showCartItemListByUserId(final HttpServletRequest request) {
        final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        final String email = authInfo.getEmail();
        final Long userId = userService.findByEmail(email).getUserId();

        return ResponseEntity.ok(cartService.findByUserId(userId));
    }
}
