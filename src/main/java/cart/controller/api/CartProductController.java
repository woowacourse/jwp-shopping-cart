package cart.controller.api;

import cart.controller.auth.AuthorizationExtractor;
import cart.controller.auth.BasicAuthorizationExtractor;
import cart.dto.AuthInfo;
import cart.dto.ProductResponse;
import cart.service.CartProductService;
import cart.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart/products")
public class CartProductController {

    private final AuthorizationExtractor<AuthInfo> basicAuthorizationExtractor = new BasicAuthorizationExtractor();

    private final ProductService productService;
    private final CartProductService cartProductService;

    public CartProductController(final ProductService productService,
                                 final CartProductService cartProductService) {
        this.productService = productService;
        this.cartProductService = cartProductService;
    }

    @PostMapping
    public ResponseEntity<Void> postCartProduct(@RequestBody final Long productId,
                                                final HttpServletRequest request) {
        final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);

        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();
        cartProductService.addCartProduct(productId, email, password);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getCartProduct(final HttpServletRequest request) {
        final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);

        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();
        final List<Long> productIds = cartProductService.findAllProductIds(email, password);

        return ResponseEntity.ok(productService.findByIds(productIds));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable final Long productId,
                                                  final HttpServletRequest request) {
        final AuthInfo authInfo = basicAuthorizationExtractor.extract(request);

        final String email = authInfo.getEmail();
        final String password = authInfo.getPassword();
        cartProductService.delete(productId, email, password);

        return ResponseEntity.noContent().build();
    }
}
