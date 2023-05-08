package cart.controller.cart;

import cart.domain.cart.Cart;
import cart.domain.product.Product;
import cart.dto.cart.AddCartResponse;
import cart.dto.cart.AddProductRequest;
import cart.dto.cart.FindCartResponse;
import cart.resolver.AuthInfo;
import cart.resolver.Authentication;
import cart.service.cart.CartCommandService;
import cart.service.cart.CartQueryService;
import cart.service.product.ProductQueryService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts/cartproduct")
public class CartApiController {

    private final CartCommandService cartCommandService;
    private final ProductQueryService productQueryService;
    private final CartQueryService cartQueryService;

    public CartApiController(final CartCommandService cartCommandService,
            final ProductQueryService productQueryService,
            final CartQueryService cartQueryService) {
        this.cartCommandService = cartCommandService;
        this.productQueryService = productQueryService;
        this.cartQueryService = cartQueryService;
    }

    @PostMapping
    public ResponseEntity<AddCartResponse> addProduct(@RequestBody final AddProductRequest addProductRequest,
            @Authentication final AuthInfo authInfo) {
        final Long productId = addProductRequest.getProductId();
        final String email = authInfo.getEmail();
        final Cart cart = cartCommandService.addProduct(productId, email);
        return ResponseEntity.ok(AddCartResponse.from(cart));
    }

    @GetMapping
    public ResponseEntity<FindCartResponse> findCart(@Authentication final AuthInfo authInfo) {
        final Cart cart = cartQueryService.findByEmail(authInfo.getEmail());
        final List<Product> products = productQueryService.findAllByIds(cart.getCartProducts().getProductIds());
        return ResponseEntity.ok(FindCartResponse.of(cart, products));
    }

    @DeleteMapping("/{cartProductId}")
    public ResponseEntity<Void> deleteProduct(@Authentication final AuthInfo authInfo,
            @PathVariable final Long cartProductId) {
        final String email = authInfo.getEmail();
        cartCommandService.deleteProduct(cartProductId, email);
        return ResponseEntity.noContent().build();
    }
}
