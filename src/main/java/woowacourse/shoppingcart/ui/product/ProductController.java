package woowacourse.shoppingcart.ui.product;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.application.dto.ProductDetailServiceResponse;
import woowacourse.shoppingcart.ui.dto.request.Request;
import woowacourse.shoppingcart.ui.product.dto.request.ProductRegisterRequest;
import woowacourse.shoppingcart.ui.product.dto.response.ProductResponse;
import woowacourse.shoppingcart.ui.product.dto.response.ProductsResponse;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> register(
            @Validated(Request.allProperties.class) @RequestBody final ProductRegisterRequest request) {
        final Long productId = productService.addProduct(request.toServiceDto());
        return ResponseEntity.created(URI.create("/api/products/" + productId)).build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> showProduct(@PathVariable final Long productId) {
        ProductDetailServiceResponse serviceResponse = productService.findProductById(productId);
        return ResponseEntity.ok(ProductResponse.from(serviceResponse));
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> showProducts(@RequestParam final int page,
                                                         @RequestParam(required = false, defaultValue = "10") final int limit) {
        final List<ProductDetailServiceResponse> products = productService.findProducts(page, limit);
        final List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ProductsResponse.from(productResponses));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable final Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
