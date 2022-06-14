package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.dto.Request;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final int DEFAULT_QUANTITY = 0;

    private final ProductService productService;
    private final CartService cartService;
    private final JwtTokenProvider jwtTokenProvider;

    public ProductController(final ProductService productService, CartService cartService,
                             JwtTokenProvider jwtTokenProvider) {
        this.productService = productService;
        this.cartService = cartService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> products(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        List<ProductResponse> productResponses = getProductResponses(token);
        return ResponseEntity.ok(new ProductsResponse(productResponses));
    }

    @PostMapping
    public ResponseEntity<Void> add(@Validated(Request.allProperties.class) @RequestBody final Product product) {
        final Long productId = productService.addProduct(product);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + productId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> product(@PathVariable final Long productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable final Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    private List<ProductResponse> getProductResponses(String token) {
        List<Product> products = productService.findProducts();
        if (token == null) {
            return products.stream()
                    .map(product -> new ProductResponse(product, DEFAULT_QUANTITY))
                    .collect(Collectors.toList());
        }
        List<Cart> carts = cartService.findCartsByCustomerId(Long.valueOf(jwtTokenProvider.getPayload(token)));
        Map<Long, Integer> quantityMap = carts.stream()
                .collect(Collectors.toMap(Cart::getProductId, Cart::getQuantity));
        return products.stream()
                .map(product -> new ProductResponse(product, getCartItemQuantity(quantityMap, product)))
                .collect(Collectors.toList());
    }

    private int getCartItemQuantity(Map<Long, Integer> quantityMap, Product product) {
        if (quantityMap.containsKey(product.getId())) {
            return quantityMap.get(product.getId());
        }
        return DEFAULT_QUANTITY;
    }
}
