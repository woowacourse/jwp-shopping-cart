package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.dto.PageRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductSaveRequest;
import woowacourse.shoppingcart.dto.ProductsResponse;
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

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody @Valid final ProductSaveRequest request) {
        final Long productId = productService.addProduct(request);
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + productId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getOne(@PathVariable final Long id) {
        final ProductResponse response = productService.findById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> getAllByPage(
            @RequestParam(value = "page", defaultValue = "1") final Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final Integer size) {
        final PageRequest pageRequest = new PageRequest(page, size);
        final List<ProductResponse> responses = productService.findAllByPage(pageRequest);

        return ResponseEntity.ok(ProductsResponse.from(responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
