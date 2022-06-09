package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.application.dto.response.ProductResponse;
import woowacourse.shoppingcart.application.ProductService;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
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
