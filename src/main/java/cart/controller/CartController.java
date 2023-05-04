package cart.controller;

import cart.auth.Auth;
import cart.auth.AuthInfo;
import cart.controller.dto.CartProductRequest;
import cart.controller.dto.ProductResponse;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cart/products")
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@Auth final AuthInfo authInfo) {
        List<ProductResponse> productByEmail = cartService.findByEmail(authInfo.getEmail());

        return ResponseEntity
                .ok()
                .body(productByEmail);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(
            @Auth final AuthInfo authInfo,
            @RequestBody final CartProductRequest cartProductRequest
    ) {
        cartService.save(authInfo.getEmail(), cartProductRequest.getProductId());

        return ResponseEntity
                .created(URI.create("/cart/products"))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId, @Auth final AuthInfo authInfo) {
        cartService.delete(authInfo.getEmail(), productId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
