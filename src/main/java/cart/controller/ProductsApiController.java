package cart.controller;

import cart.controller.dto.ProductRequest;
import cart.controller.dto.ProductResponse;
import cart.service.ProductService;
import cart.service.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductsApiController {

    private static final String PRODUCTS_LOCATION_PREFIX = "/products/";

    private final ProductService productService;

    public ProductsApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> insertProduct(@Valid @RequestBody final ProductRequest productRequest) {
        final Long productId = productService.insertProduct(new ProductDto.Builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .imageUrl(productRequest.getImageUrl())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", PRODUCTS_LOCATION_PREFIX + productId)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> readAllProducts() {
        final List<ProductResponse> response = productService.findAll()
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable final Long id,
            @Valid @RequestBody final ProductRequest productRequest
    ) {
        productService.updateById(new ProductDto.Builder()
                .id(id)
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .imageUrl(productRequest.getImageUrl())
                .build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
