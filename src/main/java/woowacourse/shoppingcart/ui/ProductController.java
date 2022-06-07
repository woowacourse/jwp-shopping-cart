package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
import woowacourse.shoppingcart.dto.Request;

@RestController
@RequestMapping("/products")
public class ProductController {

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
    public ResponseEntity<List<ProductResponse>> products(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        List<Product> products = productService.findProducts();
        if (token == null) {
            return ResponseEntity.ok(products.stream()
                    .map(product -> new ProductResponse(product, false))
                    .collect(Collectors.toList()));
        }
        List<Cart> carts = cartService.findCartsByCustomerId(Long.valueOf(jwtTokenProvider.getPayload(token)));
        List<Long> productIdsInCart = carts.stream().map(Cart::getProductId).collect(Collectors.toList());
        return ResponseEntity.ok(products.stream()
                .map(product -> new ProductResponse(product, productIdsInCart.contains(product.getId())))
                .collect(Collectors.toList()));
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
}
