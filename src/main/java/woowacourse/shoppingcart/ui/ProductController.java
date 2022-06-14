package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody ProductRequest productRequest) {
        final Long productId = productService.addProduct(productRequest);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + productId)
                .build()
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> product(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> products() {
        return ResponseEntity.ok(productService.findProducts());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
