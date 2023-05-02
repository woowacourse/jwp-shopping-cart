package cart.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import cart.domain.product.Product;
import cart.dto.ProductResponse;
import cart.dto.UserResponse;
import cart.infrastructure.BasicAuthorizationExtractor;
import cart.service.AuthService;
import cart.service.CartSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartSearchController {

    private final BasicAuthorizationExtractor basicAuthorizationExtractor = new BasicAuthorizationExtractor();
    private final AuthService authService;
    private final CartSearchService cartSearchService;

    public CartSearchController(final AuthService authService, final CartSearchService cartSearchService) {
        this.authService = authService;
        this.cartSearchService = cartSearchService;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<ProductResponse>> getCarts(final HttpServletRequest request) {
        final UserResponse extract = basicAuthorizationExtractor.extract(request);
        final String email = extract.getEmail();
        final String password = extract.getPassword();
        if (authService.isValidLogin(email, password)) {
            final List<Product> products = cartSearchService.findByEmail(email);
            final List<ProductResponse> productResponses = products.stream()
                    .map(ProductResponse::from)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(productResponses);
        }
        throw new LoginFailException();
    }
}