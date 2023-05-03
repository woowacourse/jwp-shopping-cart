package cart.controller.api;

import cart.controller.auth.AuthorizationExtractor;
import cart.controller.auth.BasicAuthorizationExtractor;
import cart.dto.AuthInfo;
import cart.dto.ProductResponse;
import cart.service.CartManagementService;
import cart.service.ProductManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart/products")
public class CartProductController {

    private final AuthorizationExtractor<AuthInfo> basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    private final ProductManagementService productManagementService;
    private final CartManagementService cartManagementService;

    public CartProductController(final ProductManagementService productManagementService,
                                 final CartManagementService cartManagementService) {
        this.productManagementService = productManagementService;
        this.cartManagementService = cartManagementService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getCartProduct(final HttpServletRequest request) {
        final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);

        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();
        final List<Long> productIds = cartManagementService.findAll(email, password);

        return ResponseEntity.ok(productManagementService.findByIds(productIds));
    }
}
