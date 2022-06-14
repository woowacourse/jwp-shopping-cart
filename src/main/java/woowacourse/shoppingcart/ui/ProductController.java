package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ProductsResponse products() {
        return productService.findProducts();
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody final ProductRequest product) {
        final long productId = productService.addProduct(product);
        final URI uri = UriCreator.withCurrentPath(String.format("/%d", productId));

        return ResponseEntity.created(uri).build();
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
