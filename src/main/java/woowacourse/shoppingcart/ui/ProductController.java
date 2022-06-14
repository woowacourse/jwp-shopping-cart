package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> products() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProductResponse>> productsByCustomer(@AuthenticationPrincipal final Customer customer) {
        return ResponseEntity.ok(productService.findProductsByCustomer(customer));
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody final ProductRequest.AllProperties productRequest) {
        final Long productId = productService.addProduct(productRequest);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + productId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> product(@PathVariable final Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable final Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}
