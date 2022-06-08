package woowacourse.shoppingcart.ui;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.FindAllProductsResponse;
import woowacourse.shoppingcart.dto.Request;
import woowacourse.shoppingcart.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<FindAllProductsResponse> products(@RequestParam("size") int size,
                                                            @RequestParam("page") int page
    ) {
        var products = productService.findProducts(size, page);

        return ResponseEntity.ok(new FindAllProductsResponse(products));
    }

    @GetMapping("/all")
    public ResponseEntity<FindAllProductsResponse> productAll() {
        var products = productService.findAllProduct();

        return ResponseEntity.ok(new FindAllProductsResponse(products));
    }

    @PostMapping
    public ResponseEntity<Void> add(@Validated(Request.allProperties.class) @RequestBody final Product product) {
        final Long productId = productService.addProduct(product);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + productId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> product(@PathVariable final Long productId) {
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable final Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
