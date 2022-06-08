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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.product.ProductRequest;
import woowacourse.shoppingcart.dto.product.ProductResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(@RequestBody final ProductRequest productRequest) {
        final Long productId = productService.addProduct(productRequest);
        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> showProducts(@RequestParam("page") Long pageNumber,
                                                              @RequestParam("limit") Long limitCount) {
        return ResponseEntity.ok(productService.findProducts(pageNumber, limitCount));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> showProduct(@PathVariable final Long productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }
}
