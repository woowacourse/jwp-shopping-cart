package woowacourse.shoppingcart.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.dto.PageRequest;
import woowacourse.shoppingcart.dto.ProductRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.addProduct(productRequest);
        return ResponseEntity.created(URI.create("/products" + product.getId())).build();
    }

    @GetMapping
    public ResponseEntity<Products> products(@ModelAttribute PageRequest pageRequest) {
        return ResponseEntity.ok(productService.findProducts(pageRequest));
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
