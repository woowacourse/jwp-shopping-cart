package woowacourse.shoppingcart.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Product;
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
        List<Product> products = productService.findProducts();
        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ProductsResponse(productResponses));
    }

//    @PostMapping
//    public ResponseEntity<Void> add(@Validated(Request.allProperties.class) @RequestBody final Product product) {
//        final Long productId = productService.addProduct(product);
//        final URI uri = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/" + productId)
//                .build().toUri();
//        return ResponseEntity.created(uri).build();
//    }
//
//    @GetMapping("/{productId}")
//    public ResponseEntity<Product> product(@PathVariable final Long productId) {
//        return ResponseEntity.ok(productService.findProductById(productId));
//    }
//
//    @DeleteMapping("/{productId}")
//    public ResponseEntity<Void> delete(@PathVariable final Long productId) {
//        productService.deleteProductById(productId);
//        return ResponseEntity.noContent().build();
//    }
}
