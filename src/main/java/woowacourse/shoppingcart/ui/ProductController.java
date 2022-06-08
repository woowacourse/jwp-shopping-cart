package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.auth.support.AuthorizationExtractor;
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
    public ResponseEntity<ProductsResponse> products(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        return ResponseEntity.ok().body(productService.findProducts(token));
    }
}
