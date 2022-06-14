package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.MemberArgument;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.product.ProductsResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> products(@MemberArgument Long customerId) {
        return ResponseEntity.ok().body(productService.findProducts(customerId));
    }
}
