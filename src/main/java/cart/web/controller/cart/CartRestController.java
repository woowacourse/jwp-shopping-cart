package cart.web.controller.cart;

import cart.domain.cart.CartService;
import cart.domain.product.Product;
import cart.web.controller.auth.BasicAuthorizationExtractor;
import cart.web.controller.product.dto.ProductResponse;
import cart.web.controller.user.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProduct(final HttpServletRequest request,
                                             @PathVariable Long productId) {
        final BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor();
        final UserRequest userRequest = extractor.extract(request);

        final Long addedProductId = cartService.add(userRequest, productId);

        return ResponseEntity.created(URI.create("/cart/" + addedProductId)).build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(final HttpServletRequest request,
                                                @PathVariable Long productId) {
        final BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor();
        final UserRequest userRequest = extractor.extract(request);

        cartService.delete(userRequest, productId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getProducts(final HttpServletRequest request) {
        final BasicAuthorizationExtractor extractor = new BasicAuthorizationExtractor();
        final UserRequest userRequest = extractor.extract(request);

        final List<Product> products = cartService.getProducts(userRequest);
        final List<ProductResponse> productResponses = products.stream()
                .map(product -> new ProductResponse(product.getId(), product.getProductNameValue(),
                        product.getImageUrlValue(), product.getPriceValue(), product.getCategory()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productResponses);
    }
}
