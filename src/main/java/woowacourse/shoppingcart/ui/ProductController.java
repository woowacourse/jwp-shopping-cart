package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.MemberArgument;
import woowacourse.shoppingcart.dto.product.ProductsResponse;
import woowacourse.shoppingcart.application.ProductService;

import javax.servlet.http.HttpServletRequest;

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
