package cart.controller.cart;

import cart.auth.info.AuthInfo;
import cart.auth.resolver.Authentication;
import cart.domain.cart.Cart;
import cart.dto.cart.AddCartResponse;
import cart.dto.cart.AddProductRequest;
import cart.dto.cart.FindCartResponse;
import cart.service.cart.CartCommandService;
import cart.service.cart.CartQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartApiController {

    private final CartCommandService cartCommandService;
    private final CartQueryService cartQueryService;

    public CartApiController(final CartCommandService cartCommandService, final CartQueryService cartQueryService) {
        this.cartCommandService = cartCommandService;
        this.cartQueryService = cartQueryService;
    }

    @PostMapping("/carts")
    public ResponseEntity<AddCartResponse> addProduct(@RequestBody final AddProductRequest addProductRequest,
            @Authentication final AuthInfo authInfo) {
        final Long productId = addProductRequest.getProductId();
        final String email = authInfo.getEmail();
        final Cart cart = cartCommandService.addProduct(productId, email);
        return ResponseEntity.ok(AddCartResponse.from(cart));
    }

    @GetMapping("/carts")
    public ResponseEntity<FindCartResponse> findCart(@Authentication final AuthInfo authInfo) {
        final Cart cart = cartQueryService.findByEmail(authInfo.getEmail());
        return ResponseEntity.ok(FindCartResponse.from(cart));
    }
}
