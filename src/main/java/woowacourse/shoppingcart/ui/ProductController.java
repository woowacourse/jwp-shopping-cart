package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.shoppingcart.application.dto.request.ProductRequest;
import woowacourse.shoppingcart.application.dto.request.Request;
import woowacourse.shoppingcart.application.dto.response.ProductResponse;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Product;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ResponseEntity<Void> add(@Validated(Request.allProperties.class) @RequestBody final ProductRequest productRequest) {
        final Long productId = productService.addProduct(productRequest);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + productId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> findById(@PathVariable final Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping(value = "/products", params = {"page", "limit"})
    public ResponseEntity<List<ProductResponse>> findProductsInPage(@RequestParam final Long page, @RequestParam final Long limit) {
        return ResponseEntity.ok(productService.findProductsInPage(page, limit));
    }
}
