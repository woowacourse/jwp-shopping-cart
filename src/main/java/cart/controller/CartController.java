package cart.controller;

import cart.auth.AuthInfo;
import cart.auth.BasicAuthorizationExtractor;
import cart.domain.cart.CartItem;
import cart.dto.CartItemRequest;
import cart.service.CartService;
import cart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
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

    @PostMapping("/items")
    public ResponseEntity<Void> createCartItem(@RequestBody final CartItemRequest cartItemRequset, final HttpServletRequest httpServletRequest) {
        final Long userId = extractUserId(httpServletRequest);
        final Long productId = cartItemRequset.getProductId();
        cartService.save(userId, productId);
        return ResponseEntity.ok().build();
    }

    private Long extractUserId(final HttpServletRequest request) {
        final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);
        final String email = authInfo.getEmail();
        return userService.findByEmail(email).getId();
    }

    @GetMapping
    public String getSettingsPage() {
        return "cart";
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> showCartItemList(final HttpServletRequest request) {
        final Long userId = extractUserId(request);
        return ResponseEntity.ok(cartService.findByUserId(userId));
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable @NotNull final Long id) {
        cartService.delete(id);
        return ResponseEntity.ok().build();
    }
}
