package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
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
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.Request;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> products() {
        return ResponseEntity.ok(productService.findProducts());
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProductResponse>> productsByCustomer(
            @AuthenticationPrincipal final LoginCustomer loginCustomer) {
        return ResponseEntity.ok(productService.findProductsByCustomerId(loginCustomer.getId()));
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
