package cart.web.controller.cart;

import cart.domain.cart.CartService;
import cart.web.controller.user.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(final CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<String> addCart(
            final HttpServletRequest request,
            @PathVariable Long productId) {
        final BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor();
        final UserRequest userRequest = extractor.extract(request);
        cartService.add(userRequest, productId);

        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }
}
