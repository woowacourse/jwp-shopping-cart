package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> products() {
        final List<Product> products = productService.findProducts();
        final List<ProductResponse> responses = products.stream()
                .map(product -> ProductResponse.of(product))
                .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> product(@PathVariable final Long productId) {
        final Product product = productService.findById(productId);
        return ResponseEntity.ok().body(ProductResponse.of(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> add(@Valid @RequestBody final ProductRequest request) {
        final Long productId = productService.addProduct(request);
        final Product product = productService.findById(productId);
        return ResponseEntity.created(URI.create("/api/products/" + productId))
                .body(ProductResponse.of(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable final Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}
