package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> products() {
        return ResponseEntity.ok(productService.findProducts());
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody ProductRequest productRequest) throws URISyntaxException {
        long createdProductId = productService.addProduct(productRequest).getId();
        return ResponseEntity.created(new URI("/products/" + createdProductId)).build();
    }

    @GetMapping("/{productId}")
    public ProductResponse product(@PathVariable final Long productId) {
        return productService.findProductById(productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable final Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
