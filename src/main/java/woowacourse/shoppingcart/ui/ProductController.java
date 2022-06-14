package woowacourse.shoppingcart.ui;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.ProductResponse;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> find(final @PathVariable Long productId) {
        ProductResponse response = productService.findById(productId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> findProductsOfPage(final @RequestParam int page,
                                                                    final @RequestParam int limit) {
        List<ProductResponse> productResponses = productService.findProductsOfPage(page, limit);
        return ResponseEntity.ok().body(productResponses);
    }
}
