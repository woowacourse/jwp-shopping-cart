package woowacourse.shoppingcart.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.service.ProductService;
import woowacourse.shoppingcart.ui.dto.request.ProductCreateRequest;
import woowacourse.shoppingcart.ui.dto.response.ProductResponse;
import woowacourse.shoppingcart.ui.dto.response.ProductsResponse;

@RequestMapping("/api/products")
@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        final Product created = productService.create(productCreateRequest.toServiceRequest());

        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).build();
    }

    @GetMapping
    public ResponseEntity<ProductsResponse> findAllWithPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        final List<Product> products = productService.findAllWithPage(page, size);
        final long totalCount = productService.countAll();

        return ResponseEntity.ok(ProductsResponse.of(totalCount, toProductResponses(products)));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> findById(@PathVariable long productId) {
        final Product product = productService.findById(productId);

        return ResponseEntity.ok(toProductResponse(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteById(@PathVariable long productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    private List<ProductResponse> toProductResponses(final List<Product> products) {
        return products.stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse toProductResponse(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }
}

