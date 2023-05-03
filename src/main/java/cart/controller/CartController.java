package cart.controller;

import cart.auth.infrastructure.BasicAuthorizationExtractor;
import cart.dto.ProductId;
import cart.dto.ProductResponse;
import cart.dto.UserAuthInfo;
import cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ProductResponse>> getCartView(final HttpServletRequest request) {
        BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
        UserAuthInfo userAuthInfo = basicAuthorizationExtractor.extract(request);
        String email = userAuthInfo.getEmail();

        List<ProductResponse> products = cartService.readCart(email);

        return ResponseEntity.ok(products);
    }

    @PostMapping("/")
    public ResponseEntity<Void> addCartItem(@RequestBody ProductId productId, HttpServletRequest request) {
        BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
        UserAuthInfo userAuthInfo = basicAuthorizationExtractor.extract(request);
        String email = userAuthInfo.getEmail();
        final long cartId = cartService.addCartItem(email, productId.getId());
        return ResponseEntity.created(URI.create("/cart/product/" + cartId)).build();
    }

    @DeleteMapping("/cart/product/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id, HttpServletRequest request) {
        BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
        UserAuthInfo userAuthInfo = basicAuthorizationExtractor.extract(request);
        String email = userAuthInfo.getEmail();

        cartService.deleteCartItem(email, id);

        return ResponseEntity.noContent().build();
    }
}
