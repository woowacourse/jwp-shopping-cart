package cart.controller.api;

import cart.auth.Auth;
import cart.auth.AuthMember;
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
    public ResponseEntity<List<ProductResponse>> getProducts(@Auth final AuthMember authMember) {
        List<ProductResponse> productResponses = cartService.findByEmail(authMember.getEmail());

        return ResponseEntity
                .ok()
                .body(productResponses);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(
            @Auth final AuthMember authMember,
            @RequestBody final CartProductRequest cartProductRequest
    ) {
        cartService.save(authMember.getEmail(), cartProductRequest.getProductId());

        return ResponseEntity
                .created(URI.create("/cart/products"))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId, @Auth final AuthMember authMember) {
        cartService.delete(authMember.getEmail(), productId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
