package cart.controller;

import cart.auth.AuthenticateUser;
import cart.dto.AuthUser;
import cart.dto.CartResponses;
import cart.dto.CartSaveRequest;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static java.lang.String.format;

@Controller
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String cartHome() {
        return "cart";
    }

    @GetMapping("/users")
    public ResponseEntity<CartResponses> getCartsAllByUserId(@AuthenticateUser AuthUser user) {
        final CartResponses cartResponses = cartService.findAllByUserId(user.getId());
        return ResponseEntity.ok(cartResponses);
    }

    @PostMapping("/users/products/{productId}")
    public ResponseEntity<URI> addProduct(@AuthenticateUser AuthUser user, @PathVariable Long productId) {
        final CartSaveRequest saveRequest = new CartSaveRequest(user.getId(), productId, 0);

        final Long id = cartService.save(saveRequest);

        final String createdUri = format("/carts/users/%s/products/%s", user.getId(), productId);
        return ResponseEntity.created(URI.create(createdUri)).build();
    }
}
