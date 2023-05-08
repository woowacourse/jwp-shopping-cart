package cart.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import cart.controller.auth.AuthenticationPrincipal;
import cart.controller.dto.ProductResponse;
import cart.domain.cart.Cart;
import cart.domain.user.User;
import cart.service.CartSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartSearchController {

    private final CartSearchService cartSearchService;

    public CartSearchController(final CartSearchService cartSearchService) {
        this.cartSearchService = cartSearchService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ProductResponse>> getCarts(@AuthenticationPrincipal final User user) {
        final String email = user.getEmail().getValue();
        final List<Cart> products = cartSearchService.findByEmail(email);
        final List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productResponses);
    }
}
