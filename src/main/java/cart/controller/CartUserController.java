package cart.controller;

import cart.auth.AuthenticateUser;
import cart.dto.AuthUser;
import cart.dto.CartResponses;
import cart.dto.CartSaveRequest;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static java.lang.String.format;

@RestController
@RequestMapping("/carts/users")
public class CartUserController {

    private final CartService cartService;

    public CartUserController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponses> getCartsAllByUserId(@AuthenticateUser AuthUser user) {
        final CartResponses cartResponses = cartService.findAllByUserId(user.getId());
        return ResponseEntity.ok(cartResponses);
    }

    @PostMapping("/products/{productId}")
    public ResponseEntity<URI> addProduct(@AuthenticateUser AuthUser user, @PathVariable Long productId) {
        final CartSaveRequest saveRequest = new CartSaveRequest(user.getId(), productId);

        cartService.save(saveRequest);

        final String createdUri = format("/carts/users/%s/products/%s", user.getId(), productId);
        return ResponseEntity.created(URI.create(createdUri)).build();
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> removeProduct(@AuthenticateUser AuthUser user, @PathVariable Long productId) {
        cartService.delete(user.getId(), productId);
        return ResponseEntity.noContent().build();
    }
}
