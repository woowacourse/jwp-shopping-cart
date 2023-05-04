package cart.controller;

import cart.auth.Auth;
import cart.auth.Credential;
import cart.dto.CartSaveRequest;
import cart.dto.CartSearchResponse;
import cart.service.CartService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@Auth final Credential credential, @RequestBody final CartSaveRequest request) {
        final Long id = cartService.save(credential.getId(), request);
        return ResponseEntity.created(URI.create("/carts/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<CartSearchResponse> findAll(@Auth final Credential credential) {
        final CartSearchResponse result = cartService.findAll(credential.getId());
        return ResponseEntity.ok(result);
    }
}
