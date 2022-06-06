package woowacourse.shoppingcart.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.application.dto.ProductServiceResponse;
import woowacourse.shoppingcart.dto.PageRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductSaveRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody @Valid final ProductSaveRequest request) {
        final Long productId = productService.addProduct(request.toServiceDto());
        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + productId)
                .build().toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getOne(@PathVariable final Long id) {
        final ProductServiceResponse serviceResponse = productService.findById(id);
        final ProductResponse response = ProductResponse.from(serviceResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllByPage(
            @RequestParam(value = "page", defaultValue = "1") final Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final Integer size) {
        final PageRequest pageRequest = new PageRequest(page, size);
        final List<ProductServiceResponse> serviceResponses = productService.findAllByPage(pageRequest.toServiceDto());
        final List<ProductResponse> responses = serviceResponses.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
