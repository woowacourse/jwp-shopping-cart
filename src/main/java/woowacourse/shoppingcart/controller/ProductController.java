package woowacourse.shoppingcart.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.auth.support.OptionalUserNameResolver;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.CreateProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(
            @OptionalUserNameResolver final Optional<UserName> customerName) {
        return customerName.map(userName -> ResponseEntity.ok(productService.findProductsByCustomerName(userName)))
                .orElseGet(() -> ResponseEntity.ok(productService.findProducts()));
    }

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody final CreateProductRequest request) {
        final Long productId = productService.addProduct(request);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + productId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> get(@OptionalUserNameResolver final Optional<UserName> customerName,
                                               @PathVariable final Long productId) {
        return customerName.map(
                        userName -> ResponseEntity.ok(productService.findProductByIdAndCustomerName(productId, userName)))
                .orElseGet(() -> ResponseEntity.ok(productService.findProductById(productId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable final Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
}
