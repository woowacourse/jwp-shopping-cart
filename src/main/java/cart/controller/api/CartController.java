package cart.controller.api;

import cart.auth.Auth;
import cart.auth.AuthMemberDetails;
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
    public ResponseEntity<List<ProductResponse>> getProducts(@Auth final AuthMemberDetails authMemberDetails) {
        List<ProductResponse> productResponses = cartService.findByEmail(authMemberDetails.getEmail());

        return ResponseEntity
                .ok()
                .body(productResponses);
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(
            @Auth final AuthMemberDetails authMemberDetails,
            @RequestBody final CartProductRequest cartProductRequest
    ) {
        cartService.save(authMemberDetails.getEmail(), cartProductRequest.getProductId());

        return ResponseEntity
                .created(URI.create("/cart/products"))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long productId, @Auth final AuthMemberDetails authMemberDetails) {
        cartService.delete(authMemberDetails.getEmail(), productId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
