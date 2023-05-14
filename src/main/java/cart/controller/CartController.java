package cart.controller;

import cart.global.annotation.Auth;
import cart.global.infrastructure.Credential;
import cart.service.CartService;
import cart.service.dto.cart.CartAddProductRequest;
import cart.service.dto.cart.CartAllProductSearchResponse;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carts/products")
@RestController
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartAllProductSearchResponse>> searchAll(
            @Auth Credential credential
    ) {
        return ResponseEntity
                .ok()
                .body(cartService.searchAllCartProducts(credential));
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(
            @Auth Credential credential,
            @RequestBody CartAddProductRequest request
    ) {
        Long savedId = cartService.save(credential, request);

        return ResponseEntity
                .created(URI.create("/carts/products/" + savedId))
                .build();
    }

    @DeleteMapping("{cartProductId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("cartProductId") final Long cartProductId) {
        cartService.deleteProduct(cartProductId);

        return ResponseEntity.noContent().build();
    }
}
